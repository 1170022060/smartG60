package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 11:54
 */
@Data
public class ToiletSensorInfo {
    private String ser_num; //厕所序号
    private ToiletSensorData sensor; //传感器数据
}