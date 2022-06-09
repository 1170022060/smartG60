package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.mapper.device.TblDeviceRepairMapper;
import com.pingok.vocational.service.device.IDeviceRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 设备维修记录 服务层处理
 *
 * @author ruoyi
 */
@Service
public class DeviceRepairServiceImpl implements IDeviceRepairService {
    @Autowired
    private TblDeviceRepairMapper tblDeviceRepairMapper;

    @Override
    public List<Map> selectDeviceRepair(Long deviceId) {
        return tblDeviceRepairMapper.selectDeviceRepair(deviceId);
    }
}
