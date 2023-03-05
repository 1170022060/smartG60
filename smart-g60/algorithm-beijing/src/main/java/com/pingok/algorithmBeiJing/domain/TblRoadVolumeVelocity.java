package com.pingok.algorithmBeiJing.domain;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 路段流量流速计算结果(TBL_ROAD_VOLUME_VELOCITY)
 * 
 * @author bianj
 * @version 1.0.0 2023-03-02
 */
@Data
@Table(name = "TBL_ROAD_VOLUME_VELOCITY")
public class TblRoadVolumeVelocity implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -1927563406125713266L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 路段ID */
    private String roadId;

    /** 流速 */
    private BigDecimal velocity;

    /** 流量 */
    private BigDecimal flow;

    /** 拥堵等级 */
    private BigDecimal congestion;

    /** startTime */
    private Date startTime;

    /** endTime */
    private Date endTime;

}