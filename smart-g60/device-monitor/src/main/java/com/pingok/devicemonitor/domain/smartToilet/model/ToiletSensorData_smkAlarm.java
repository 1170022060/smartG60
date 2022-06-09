package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:38
 */
@Data
public class ToiletSensorData_smkAlarm {
    private Integer status; //烟雾报警状态（0：无报警 其他：报警）
}
