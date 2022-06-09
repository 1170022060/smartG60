package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:39
 */
@Data
public class ToiletSensorData_pm {
    private Float value; //纸张剩余量(0~100对应 0% -> 100%)
}