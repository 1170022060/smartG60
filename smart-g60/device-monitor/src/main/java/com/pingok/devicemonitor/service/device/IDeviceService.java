package com.pingok.devicemonitor.service.device;

import com.pingok.devicemonitor.domain.device.TblDeviceFault;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;

import java.util.List;

public interface IDeviceService {

    void deviceFault(TblDeviceFault deviceFault);
    /**
     * 设备心跳
     *
     * @param deviceStatus
     */
    void updateStatus(TblDeviceStatus deviceStatus);

    /**
     * 查询所有设备
     * @return
     */
    List<TblDeviceInfo> findByProtocol(String protocol);
}
