package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:38
 */
@Data
public class ToiletSensorData_cubicle {
    private Integer gender; //0x10(男) 0x20(女) 0x30(其他)
    private Integer index; //0-255(厕位编号)
    private Integer status; //0 (未占用) 1(占用)
}
