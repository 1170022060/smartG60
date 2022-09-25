package com.pingok.devicemonitor.service.pilotLight;

import com.pingok.devicemonitor.domain.device.TblDeviceStatus;

import java.util.List;

public interface IPilotLightService {
    void collectRtStatus();
    List<TblDeviceStatus> getRtStatus(Integer roadId);
}
