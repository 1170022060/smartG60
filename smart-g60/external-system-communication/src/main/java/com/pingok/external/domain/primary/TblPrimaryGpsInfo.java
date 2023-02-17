package com.pingok.external.domain.primary;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 两客一危GPS信息表(TBL_PRIMARY_GPS_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_PRIMARY_GPS_INFO")
public class TblPrimaryGpsInfo implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1850775880408983003L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 车牌号 */
    private String license;

    /** 车牌颜色 */
    private Integer color;

    /** 原始经度 */
    private Long lon;

    /** 原始纬度 */
    private Long lat;

    /** gps时间 */
    private Date time;

    /** gps速度 */
    private Long speed;

    /** 方向 */
    private Long direction;

    /** 高度（海拔） */
    private Long altitude;

    /** acc状态,0关 1开 */
    private Integer acc;

}