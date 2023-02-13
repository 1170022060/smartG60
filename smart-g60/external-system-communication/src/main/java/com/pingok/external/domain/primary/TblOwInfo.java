package com.pingok.external.domain.primary;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 超限车辆信息表(TBL_OW_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_OW_INFO")
public class TblOwInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -7929402210159746547L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 数据唯一标识 */
    private Long uniqueId;

    /** 治超站或高速收费站名称 */
    private String siteName;

    /** 站点编号或高速公路收费站编号 */
    private String siteCode;

    /** 称重检测设备编号 */
    private String equipCode;

    /** 检测时间 */
    private Date checkTime;

    /** 车辆号牌 */
    private String vehicleNo;

    /** 车牌颜色 */
    private Long plateColor;

    /** 车型代码 */
    private String vehicleAxlesType;

    /** 车货总质量 */
    private Long total;

    /** 车辆轴数 */
    private Long axles;

    /** 入口车速 */
    private Long speed;

    /** 最大允许总质量 */
    private Long limitWeight;

    /** 超限量 */
    private Long overWeight;

    /** 超限超载率 */
    private Long overRate;

    /** 流转状态(1 未处置 2 立案 3 不予立案 4 结案) */
    private Integer flowStatus;

    /** 车牌图片 */
    private String platePic;

    /** 前抓拍 */
    private String firstHeaderPic;

    /** 正侧抓拍 */
    private String degree45Pic;

    /** 侧后抓拍 */
    private String sidePic;

    /** 视频 */
    private String video;

}