package com.pingok.devicemonitor.domain.smartToilet.model;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @time 2022/5/16 12:27
 */

@Data
public class ToiletSensorData {
    private String type; //传感器类型
    private Integer index; //传感器编号
    private Long time; //活跃时间
    private Integer power; //电量
    private ToiletSensorData_idtk idtk; //人流量
    private Float nh3; //氨气数值（ppm）
    private Float h2s; //硫化氢数值（ppm)
    private Float hum; //湿度值（%）
    private Float temp; //温度（℃）
    private ToiletSensorData_co2 co2; //二氧化碳
    private Float pm25; //pm2.5数值（ug/m3）
    private Float voc; //tvoc数值（ppm）
    private List<ToiletSensorData_cubicle> cubicles; //坑位占用情况
    private ToiletSensorData_alarm alarm; //厕所报警
    private ToiletSensorData_smkAlarm smk_alarm; //烟雾报警
    private ToiletSensorData_wm wm; //水表
    private ToiletSensorData_em em; //电表
    private ToiletSensorData_pm pm; //取纸巾
    private ToiletSensorData_evl evl; //评价器
}
