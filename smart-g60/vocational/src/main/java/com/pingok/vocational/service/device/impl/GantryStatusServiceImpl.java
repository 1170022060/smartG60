package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblGantryStatus;
import com.pingok.vocational.mapper.device.TblGantryStatusMapper;
import com.pingok.vocational.service.device.IGantryStatusService;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 门架设备状态 服务层处理
 *
 * @author ruoyi
 */
@Service
public class GantryStatusServiceImpl implements IGantryStatusService {
    @Autowired
    private TblGantryStatusMapper tblGantryStatusMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public int insertGantryStatus(Long deviceId) {
        TblGantryStatus tblGantryStatus=new TblGantryStatus();
        tblGantryStatus.setId(remoteIdProducerService.nextId());
        tblGantryStatus.setDeviceId(deviceId);
        tblGantryStatus.setStatus(0);
        tblGantryStatus.setStatusDesc("未知");
        return tblGantryStatusMapper.insert(tblGantryStatus);
    }
}
