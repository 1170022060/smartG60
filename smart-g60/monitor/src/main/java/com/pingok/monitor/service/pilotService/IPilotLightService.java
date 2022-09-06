package com.pingok.monitor.service.pilotService;

import com.pingok.monitor.domain.device.TblDeviceStatus;

import java.util.List;

public interface IPilotLightService {
    List<TblDeviceStatus> getRtStatus(Integer roadId);
}
