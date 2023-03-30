package com.pingok.vocational.domain.nameList;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TBL_RATE_STATION_USED
 * 
 * @author bianj
 * @version 1.0.0 2022-10-25
 */
@Data
@Table(name = "TBL_RATE_STATION_USED")
public class TblRateStationUsed implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5504175896811267518L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private BigDecimal id;

    /** 收费站Hex码 */
    private String stationHex;

    /** 名单版本 */
    private String version;

    /** 创建时间 */
    private Date createTime;

    /** 使用时间 */
    private Date applyTime;

}