package com.pingok.monitor.service.bridge;

import com.pingok.monitor.domain.bridge.DeviceStatus;

import java.util.List;
import java.util.Map;


public interface IBridgeStatusService {

    /**
     * 桥梁监控设备状态
     * @param id
     * @return
     */
    DeviceStatus deviceStatus(Long id);

    /**
     * 桥梁状态列表
     * @param name
     * @return
     */
    List<Map> list(String name);
}
