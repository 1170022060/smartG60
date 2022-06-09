package com.pingok.monitor.domain.bridge;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeviceStatus {

    /**
     * 传感器状态
     */
    private List<Map> acquisition;

    /**
     * 采集仪状态
     */
    private List<Map> collection;
}
