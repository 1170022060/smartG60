package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:38
 */
@Data
public class ToiletSensorData_wm {
    private Float value; //总用水量(当前水表读数)
    private Float power; //电池电量
    private Integer status; //水表状态(0：正常，1:磁干扰)
}