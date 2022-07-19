package com.pingok.monitor.service.vdt.Impl;

import com.alibaba.fastjson.JSON;
import com.pingok.monitor.domain.device.TblDeviceInfo;
import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.domain.vdt.TblVdtStatusLog;
import com.pingok.monitor.domain.vdt.VDComplexStatus;
import com.pingok.monitor.domain.vdt.VDFlowStatus;
import com.pingok.monitor.domain.vdt.VDStatus;
import com.pingok.monitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.monitor.mapper.vdt.TblVdtStatusLogMapper;
import com.pingok.monitor.service.common.IModbusService;
import com.pingok.monitor.service.vdt.IVdtService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.NetUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.serotonin.modbus4j.code.DataType;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.internals.Heartbeat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.pingok.monitor.utils.ByteUtils.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @time 2022/7/6 11:22
 */
@Slf4j
@Service
public class VdtServiceImpl implements IVdtService {

    @Autowired
    TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    TblVdtStatusLogMapper tblVdtStatusLogMapper;
    @Autowired
    IModbusService iModbusService;
    @Autowired
    RemoteIdProducerService idProducerService;

    @Override
    public void collect() {
        log.info("车检器->开始采集...");

        //设备信息
        List<TblDeviceInfo> devList = tblDeviceInfoMapper.findByProtocol("vdt.swarco");
        //采集
        try {
            for(TblDeviceInfo dev : devList) {
                if(false == NetUtil.ping(dev.getDeviceIp())) {
                    continue;
                }
                /**发送报文
                 检测单元数据块长度（2B车型n流量*3 + 1B车型n速度*3 + 1B平均速度*3 + 1B平均时间占有率*3 + 2B平均车头时距*3）
                 寄存器数量最多125个
                 */
                MbsAttribute mbs = new MbsAttribute(dev.getDeviceIp(), dev.getPort(), dev.getSlaveId(),
                        3, Integer.valueOf("1800", 16), 100, DataType.TWO_BYTE_INT_SIGNED);
                try {
                    byte[] data = iModbusService.readHoldingRegister(mbs);
                    if(data != null && data.length >= 100) {
                        //解析，参照协议《第二部分 环形线圈式车辆检测器功能要求及用户层通信规程1.1.1.doc》
                        VDStatus vdStatus = new VDStatus();
                        int pos = 5;
                        String year = toBCDStr(data, 2, pos); pos += 2;
                        String month = StringUtils.leftPad(toBCDStr(data, 1, pos++), 2, '0');
                        String day = StringUtils.leftPad(toBCDStr(data, 1, pos++),2, '0');
                        String hour = StringUtils.leftPad(toBCDStr(data, 1, pos++),2, '0');
                        String min = StringUtils.leftPad(toBCDStr(data, 1, pos++),2, '0');
                        String sec = StringUtils.leftPad(toBCDStr(data, 1, pos++),2, '0'); pos += 1;
                        vdStatus.setTime(String.format("%s/%s/%s %s:%s:%s", year,month,day,hour,min,sec));

                        //复合检测单元数量
                        Integer complexUnitCount = b2i(data[pos++]);
                        //流量检测单元数量
                        Integer flowUnitCount = b2i(data[pos++]); pos += 5;
                        //车型分类数
                        Integer carTypeCount = b2i(data[pos++]);
                        List<VDComplexStatus> complexStatusList = new ArrayList<>();
                        for(int i=0; i < complexUnitCount; ++i) {
                            VDComplexStatus complexStatus = new VDComplexStatus();
                            //车型流量
                            Integer totalVolume = 0;
                            List<Integer> volumns = new ArrayList<>();
                            for(int j=0; j < carTypeCount; ++j) {
                                Integer volume = b2i(data[pos++]) * 256 + b2i(data[pos++]);
                                totalVolume += volume;
                                volumns.add(volume);
                            }
                            complexStatus.setTotalVolume(totalVolume);
                            complexStatus.setVolumes(volumns);
                            List<Integer> speeds = new ArrayList<>();
                            //车型规律是 2，1，4，3，6，5……
                            for(int j=0; j < carTypeCount; j+=2, pos+=2) {
                                speeds.add(b2i(data[pos+1]));
                                speeds.add(b2i(data[pos]));
                            }
                            complexStatus.setSpeeds(speeds);
                            //speeds会多一个
//                            if(carTypeCount % 2 != 0) pos++;
                            complexStatus.setAvgSpeed(b2i(data[pos++]));
                            complexStatus.setAvgOccupy(b2i(data[pos++]));
                            complexStatus.setAvgVehTimeHeadway(b2i(data[pos++]) * 256 + b2i(data[pos++]));
                            complexStatusList.add(complexStatus);
                        }
                        vdStatus.setComplexStatusList(complexStatusList);

                        List<VDFlowStatus> flowStatusList = new ArrayList<>();
                        for(int i=0; i < flowUnitCount; ++i) {
                            VDFlowStatus flowStatus = new VDFlowStatus();
                            flowStatus.setTotalVolume(b2i(data[pos++]) * 256 + b2i(data[pos++]));
                            flowStatus.setAvgSpeed(b2i(data[pos++]));
                            flowStatus.setAvgOccupy(b2i(data[pos++]));
                            flowStatus.setAvgVehTimeHeadway(b2i(data[pos++]) * 256 + b2i(data[pos++]));
                            flowStatusList.add(flowStatus);
                        }
                        vdStatus.setFlowStatusList(flowStatusList);

                        //入库
                        TblVdtStatusLog rec = new TblVdtStatusLog();
                        rec.setId(idProducerService.nextId());
                        rec.setVdtId(dev.getDeviceId());
                        rec.setCreateTime(DateUtils.getNowDate());
                        rec.setDetails(JSON.toJSONString(vdStatus));
                        tblVdtStatusLogMapper.insert(rec);
                    }
                    else {
                        log.error("车检器->报文异常！");
                    }
                } catch (Exception e) {
                    log.error("车检器->采集异常：" + e.getMessage());
                    continue;
                }
            }
        } catch (Exception e) {
            log.error("车检器->采集异常：" + e.getMessage());
        }

        log.info("车检器->结束采集...");
    }
}
