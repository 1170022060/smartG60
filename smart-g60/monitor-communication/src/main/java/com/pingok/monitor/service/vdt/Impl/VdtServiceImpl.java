package com.pingok.monitor.service.vdt.Impl;

import cn.hutool.core.lang.Console;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.domain.common.MbsAttribute;
import com.pingok.monitor.domain.device.TblDeviceInfo;
import com.pingok.monitor.domain.vdt.TblVdHistoryRecord;
import com.pingok.monitor.domain.vdt.VDComplexStatus;
import com.pingok.monitor.service.common.IModbusService;
import com.pingok.monitor.service.vdt.IVdtService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.NetUtil;
import com.ruoyi.common.core.utils.StringUtils;
import com.serotonin.modbus4j.code.DataType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
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
    IModbusService iModbusService;

    @Override
    public void collect(List<TblDeviceInfo> devList) {
        log.info("车检器->开始采集...");

        if(devList == null || devList.size() == 0) {
            log.error("车检器->设备信息为空！");
            return;
        }
        //采集
        try {
            List<TblVdHistoryRecord> vdRetList = new ArrayList<>();
            for(TblDeviceInfo dev : devList) {
                if(false == NetUtil.ping(dev.getDeviceIp())) {
                    continue;
                }
                /**发送报文
                 检测单元数据块长度（2B车型n流量*3 + 1B车型n速度*3 + 1B平均速度*3 + 1B平均时间占有率*3 + 2B平均车头时距*3）
                 寄存器数量最多125个
                 */
                String remark = dev.getRemark();
                String[] splitRemark = remark.split("\\|");
                int carTypeCnt = 0;
                int detectorCnt = 0;
                if(splitRemark.length > 0) {
                    carTypeCnt = Integer.parseInt(splitRemark[0]);
                    detectorCnt = Integer.parseInt(splitRemark[1]);
                }
                int nRegCnt = 8 + (carTypeCnt + 1 + (carTypeCnt + 1) / 2 + 1) * detectorCnt;
                MbsAttribute mbs = new MbsAttribute(dev.getDeviceIp(), dev.getPort(), dev.getSlaveId(),
                        3, Integer.valueOf("1300", 16), nRegCnt, DataType.TWO_BYTE_INT_UNSIGNED);
                try {
                    short[] data = iModbusService.readHoldingRegisterByShort(mbs);
                    if(data != null && data.length >= nRegCnt) {
                        byte[] bysData = shortToByte(data); // 每个byte是十进制显示
                        String resp = bytesToHex(bysData);
                        Console.log("车检器报文：" + resp);
//                        解析，参照协议《第二部分 环形线圈式车辆检测器功能要求及用户层通信规程1.1.1.doc》
                        int pos = 8*2; //前面要跳过8个数据
                        // 这里线圈车检器上下行是一起的，要分开
                        if(detectorCnt > 5) {
                            int detCnt1 = detectorCnt / 2;
                            dev.setDirection((short)1);
                            pos = ParseData(dev, detCnt1, carTypeCnt, bysData, pos, vdRetList);

                            int detCnt2 = detectorCnt - detCnt1;
                            dev.setDirection((short)2);
                            ParseData(dev, detCnt2, carTypeCnt, bysData, pos, vdRetList);
                        }
                        else {
                            ParseData(dev, detectorCnt, carTypeCnt, bysData, pos, vdRetList);
                        }
                    }
                    else {
                        log.error("车检器->报文异常！");
                    }
                } catch (Exception e) {
                    log.error("车检器->采集异常：" + e.getMessage());
                    continue;
                }
            }
            if(vdRetList.size() > 0) {
                // 发送到 DASS
//                VDHeartbeat hb = new VDHeartbeat(JSON.toJSONString(vdRetList));
                SendToDASS(vdRetList);
            }
        } catch (Exception e) {
            log.error("车检器->采集异常：" + e.getMessage());
        }

        log.info("车检器->结束采集...");
    }

    int ParseData(TblDeviceInfo dev, int detectorCnt, int carTypeCnt, byte[] bysData, int pos, List<TblVdHistoryRecord> vdRetList) {

        String nowTime = DateUtils.dateTimeNow("yyyy-MM-dd HH:mm:ss");
        TblVdHistoryRecord vdStatus = new TblVdHistoryRecord();
        vdStatus.setDeviceId(dev.getDeviceId());
        vdStatus.setDirection((int)dev.getDirection());
        vdStatus.setPileNo(dev.getPileNo());
        vdStatus.setCollectTime(nowTime);
        List<VDComplexStatus> complexStatusList = new ArrayList<>();

        for(int i=0; i < detectorCnt; ++i) {
            VDComplexStatus complexStatus = new VDComplexStatus();
            //车型流量
            Integer totalVolume = 0;
            for(int j=0; j < carTypeCnt; ++j) {
                Integer volume = bysData[pos++] * 256 + bysData[pos++];
                totalVolume += volume;
            }
            complexStatus.setTotalVolume(totalVolume);
            //跳过车速字节
            pos += (carTypeCnt + 1) / 2 * 2;
            complexStatus.setAvgSpeed((int)bysData[pos++]);
            complexStatus.setAvgOccupy((int)bysData[pos++]);
            complexStatus.setAvgVehTimeHeadway((int)bysData[pos++] * 256 + (int)bysData[pos++]);
            complexStatusList.add(complexStatus);
        }
        vdStatus.setVolume((long)complexStatusList.stream().mapToInt(x -> x.getTotalVolume()).sum());
        if(vdStatus.getVolume() > 0) {
            vdStatus.setSpeed((long)complexStatusList.stream().mapToInt(x -> x.getAvgSpeed()).average().getAsDouble());
            vdStatus.setOccupy((long)complexStatusList.stream().mapToInt(x -> x.getAvgOccupy()).average().getAsDouble());
            vdStatus.setVh((long)complexStatusList.stream().mapToInt(x -> x.getAvgVehTimeHeadway()).average().getAsDouble());
            vdRetList.add(vdStatus);
        }
        return pos;
    }

    void SendToDASS(List<TblVdHistoryRecord> result) {
        String post;
        R ret;
        int time = 2;
        while (time-- > 0) {
            try {
                String temp = JSON.toJSONString(result);
                post = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/vdt/notifyResult", temp);
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "车检器转发失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "车检器转发状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "车检器转发异常：" + e.getMessage());
            }
        }
    }

    String bytesToHex(byte[] bytes) {
        String hex = new BigInteger(1, bytes).toString(16);
        return hex;
    }

    byte[] shortToByte(short[] data) {
        byte[] byteValue = new byte[data.length * 2];
        for (int i = 0; i < data.length; i++) {
            byteValue[i * 2 + 1] = (byte) (data[i] & 0xff);
            byteValue[i * 2] = (byte) ((data[i] & 0xff00) >> 8);
        }
        return byteValue;
    }
}
