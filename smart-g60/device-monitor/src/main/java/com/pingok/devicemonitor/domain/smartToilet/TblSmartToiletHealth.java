package com.pingok.devicemonitor.domain.smartToilet;

import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 * 智慧厕所健康情况表
 */
@Table(name="TBL_SMART_TOILET_HEALTH")
@Data
public class TblSmartToiletHealth implements Serializable {
    @Id
    private Long id;

    /**
     * 厕所ID
     */
    private Integer serId;

    /**
     * 氨气含量
     */
    private Float nh3;

    /**
     * 硫化氢含量
     */
    private Float h2s;

    /**
     * 湿度
     */
    private Float hum;

    /**
     * 温度
     */
    private Float temp;

    /**
     * 二氧化碳
     */
    private Float co2;

    /**
     * PM2.5
     */
    private Float pm25;

    /**
     * TVOC数值
     */
    private Float voc;

    /**
     * 烟雾报警：0：无报警 其他：报警
     */
    private Integer smkAlarm;

    /**
     * 用水量
     */
    private Float wm;

    /**
     * 用电量
     */
    private Float em;

    /**
     * 纸张剩余
     */
    private Float pm;

    /**
     * 评价情况
     */
    private String evl;

    private static final long serialVersionUID = 1L;
}