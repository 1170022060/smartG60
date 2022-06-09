package com.pingok.station.domain.bulkList.vo;

import lombok.Data;

import java.util.Date;
@Data
public class BulkVo {
    /**
     * 大件运输许可证号
     */
    private String certNo;

    /**
     * 途径省份编码
     */
    private String provinces;

    /**
     * 入口收费站编号
     */
    private String enStationId;

    /**
     * 入口收费站名称
     */
    private String enStationName;

    /**
     * 出口收费站编号
     */
    private String exStationId;

    /**
     * 出口收费站名称
     */
    private String exStationName;

    /**
     * 牵引车号牌/临时行驶车辆号牌
     */
    private String tractorVehicleId;

    /**
     * 挂车号牌/临时行驶车辆号牌
     */
    private String trailerVehicleId;

    /**
     * 起始通行日期
     */
    private Date startPassDate;

    /**
     * 结束通行日期
     */
    private Date endPassDate;

    /**
     * 承运单位名称
     */
    private String carriorUnit;

    /**
     * 货物名称
     */
    private String goodsInfo;

    /**
     * 车货总质量
     */
    private Integer weight;

    /**
     * 车货总体外廓尺寸-长度（车货总长度）
     */
    private Integer length;

    /**
     * 车货总体外廓尺寸-宽度（车货总宽度）
     */
    private Integer width;

    /**
     * 车货总体外廓尺寸-高度（车货总高度）
     */
    private Integer height;

    /**
     * 轴数
     */
    private Integer alexCount;

    /**
     * 轮胎数
     */
    private Integer tyleCount;

    /**
     * 各车轴轴荷
     */
    private String alexsLoad;

    /**
     * 通行路线
     */
    private String roads;

    /**
     * 通行次数
     */
    private Integer passCount;

    /**
     * 通行路线说明
     */
    private String desc;

    /**
     * 发证单位
     */
    private String orgUnit;

    /**
     * 发证日期
     */
    private String certificationDate;
}
