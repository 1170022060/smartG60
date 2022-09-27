package com.pingok.devicemonitor.service.device.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceFault;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;
import com.pingok.devicemonitor.domain.device.TblDeviceStatusLog;
import com.pingok.devicemonitor.mapper.device.TblDeviceFaultMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceStatusLogMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.devicemonitor.service.device.IDeviceService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Service
public class DeviceServiceImpl implements IDeviceService {

    @Autowired
    private TblDeviceStatusLogMapper tblDeviceStatusLogMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMappr;
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private TblDeviceFaultMapper tblDeviceFaultMapper;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public TblDeviceInfo selectByDeviceId(String deviceId) {
        Example example = new Example(TblDeviceInfo.class);
        example.createCriteria().andEqualTo("deviceId", deviceId);
        return tblDeviceInfoMapper.selectOneByExample(example);
    }

    @Override
    public void deviceFault(TblDeviceFault deviceFault) {
        Example example = new Example(TblDeviceFault.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", deviceFault.getDeviceId());
        criteria.andEqualTo("faultId", deviceFault.getFaultId());
        criteria.andEqualTo("status", 0);
        TblDeviceFault tblDeviceFault = tblDeviceFaultMapper.selectOneByExample(example);
        if (tblDeviceFault == null) {
            tblDeviceFault = new TblDeviceFault();
            BeanUtils.copyNotNullProperties(deviceFault, tblDeviceFault);
            tblDeviceFault.setId(remoteIdProducerService.nextId());
            tblDeviceFault.setCreateTime(DateUtils.getNowDate());
            tblDeviceFault.setStatus(0);
            tblDeviceFaultMapper.insert(tblDeviceFault);

            TblDeviceInfo deviceInfo = tblDeviceInfoMapper.selectByPrimaryKey(tblDeviceFault.getDeviceId());

            JSONObject fault = new JSONObject();
            fault.put("id", tblDeviceFault.getId());
            fault.put("deviceId", tblDeviceFault.getDeviceId());
            fault.put("deviceName", deviceInfo.getDeviceName());
            fault.put("locationInterval", deviceInfo.getGps());
            fault.put("faultId", tblDeviceFault.getFaultId());
            fault.put("faultDescription", tblDeviceFault.getFaultDescription());
            fault.put("time", tblDeviceFault.getFaultTime());

            JSONObject data = new JSONObject();
            data.put("type", "deviceFault");
            data.put("data", fault.toJSONString());
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
            kafkaEnum.setData(data.toJSONString());
            remoteKafkaService.send(kafkaEnum);
        } else {
            BeanUtils.copyNotNullProperties(deviceFault, tblDeviceFault);
            tblDeviceFault.setUpdateTime(DateUtils.getNowDate());
            tblDeviceFaultMapper.updateByPrimaryKey(tblDeviceFault);
        }
    }

    @Override
    public void updateStatus(TblDeviceStatus deviceStatus) {

        TblDeviceStatusLog deviceStatusLog = new TblDeviceStatusLog();
        BeanUtils.copyNotNullProperties(deviceStatus, deviceStatusLog);
        deviceStatusLog.setId(remoteIdProducerService.nextId());
        tblDeviceStatusLogMapper.insert(deviceStatusLog);

        Example example = new Example(TblDeviceStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deviceId", deviceStatus.getDeviceId());

        TblDeviceStatus ds = tblDeviceStatusMappr.selectOneByExample(example);
        if (ds == null) {
            ds = new TblDeviceStatus();
            ds.setId(remoteIdProducerService.nextId());
            BeanUtils.copyNotNullProperties(deviceStatus, ds);
            tblDeviceStatusMappr.insert(ds);
        } else {
            BeanUtils.copyNotNullProperties(deviceStatus, ds);
            tblDeviceStatusMappr.updateByPrimaryKey(ds);
        }

        KafkaEnum kafkaEnum;
        TblDeviceInfo info = tblDeviceInfoMapper.selectByPrimaryKey(deviceStatus.getDeviceId());
        if(StringUtils.isNotNull(info) && (info.getDeviceType() == 11 || info.getDeviceType() == 12)){
            kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.GIS_UPDATE_STATUS);
            JSONObject data = new JSONObject();
            data.put("code", info.getDeviceId());
            data.put("status", ds.getStatus() == 0 ? 1 : 0);
            switch (info.getDeviceType() ){
                case 11:
                    data.put("type", "vd");
                    break;
                case 12:
                    data.put("type", "light");
                    break;
            }
            kafkaEnum.setData(data.toJSONString());
            remoteKafkaService.send(kafkaEnum);
        }
    }

    @Override
    public List<TblDeviceInfo> findByProtocol(String protocol) {
        Example example = new Example(TblDeviceInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNotNull("deviceIp");
        criteria.andEqualTo("protocol", protocol);
        return tblDeviceInfoMapper.selectByExample(example);
    }
}
