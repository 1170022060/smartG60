package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

/**
 * @author
 * @time 2022/5/16 12:39
 */
@Data
public class ToiletSensorData_evl {
    private Integer excellent; //0(非常满意数量)
    private Integer good; //0(满意数量)
    private Integer smelly; //0(不满意数量，异味太重)
    private Integer inefficiency; //0(不满意数量,厕位太少)
    private Integer badattitude; //0(不满意数量，服务太差)
    private Integer poorsanitary; //0(卫生太差)
}