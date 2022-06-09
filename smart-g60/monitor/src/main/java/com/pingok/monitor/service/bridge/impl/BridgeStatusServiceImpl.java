package com.pingok.monitor.service.bridge.impl;

import com.pingok.monitor.domain.bridge.DeviceStatus;
import com.pingok.monitor.mapper.bridge.BridgeStatusMapper;
import com.pingok.monitor.service.bridge.IBridgeStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class BridgeStatusServiceImpl implements IBridgeStatusService {
    @Autowired
    private BridgeStatusMapper bridgeStatusMapper;


    @Override
    public DeviceStatus deviceStatus(Long id) {
        DeviceStatus deviceStatus = new DeviceStatus();
        deviceStatus.setAcquisition(bridgeStatusMapper.acquisition(id));
        deviceStatus.setCollection(bridgeStatusMapper.collection(id));
        return deviceStatus;
    }

    @Override
    public List<Map> list(String name) {
        return bridgeStatusMapper.list(name);
    }
}
