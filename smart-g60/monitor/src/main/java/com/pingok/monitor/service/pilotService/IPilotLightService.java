package com.pingok.monitor.service.pilotService;

import com.alibaba.fastjson.JSONArray;
import com.pingok.monitor.domain.device.TblDeviceStatus;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IPilotLightService {

    /**
     * 查询超视距诱导灯状态详情
     * @return
     */
    List<Map> pilotLightStatus();
    List<TblDeviceStatus> getRtStatus(Integer roadId);
    List<Map> visibilityTotal(Date startTime,Date endTime);
    JSONArray visibilityTrend(Date startTime, Date endTime);
}
