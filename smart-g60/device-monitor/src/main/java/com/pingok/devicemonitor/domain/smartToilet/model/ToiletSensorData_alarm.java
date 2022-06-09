package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:38
 */
@Data
public class ToiletSensorData_alarm {
    private Integer status; //报警状态（0：无报警 其他：报警）
    private Integer alarm_key; //报警键值
}
