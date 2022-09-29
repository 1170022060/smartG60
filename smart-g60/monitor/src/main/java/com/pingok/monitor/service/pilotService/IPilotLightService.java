package com.pingok.monitor.service.pilotService;

import com.pingok.monitor.domain.device.TblDeviceStatus;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface IPilotLightService {

    /**
     * 查询超视距诱导灯状态详情
     * @return
     */
    List<Map> pilotLightStatus();
    List<TblDeviceStatus> getRtStatus(Integer roadId);
}
