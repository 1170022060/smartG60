package com.pingok.monitor.service.faultAlarm;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IFaultAlarmService {

    List<Map> getFaultAlarm();

    /**
     * 告警确认
     * @param type
     * @param id
     * @param alarmStatus
     * @return
     */
    int alarmConfirm(Integer type,Long id,Integer alarmStatus);
}
