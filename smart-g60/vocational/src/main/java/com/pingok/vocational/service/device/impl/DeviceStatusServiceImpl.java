package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblDeviceStatus;
import com.pingok.vocational.mapper.device.TblDeviceStatusMapper;
import com.pingok.vocational.service.device.IDeviceStatusService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备当前状态表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService {
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public int insertDeviceStatus(Long deviceId) {
        TblDeviceStatus tblDeviceStatus=new TblDeviceStatus();
        tblDeviceStatus.setId(remoteIdProducerService.nextId());
        tblDeviceStatus.setDeviceId(deviceId);
        tblDeviceStatus.setStatus(0);
        tblDeviceStatus.setStatusDesc("未知");
        return tblDeviceStatusMapper.insert(tblDeviceStatus);
    }
}
