package com.pingok.vocational.domain.nameList;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 状态名单表（收费站在用的）(TBL_BLACK_CARD_STATION_USED)
 * 
 * @author bianj
 * @version 1.0.0 2022-10-21
 */
@Data
@Table(name = "TBL_BLACK_STATION_USED")
public class TblBlackCardStationUsedN implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2387147646897099985L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private BigDecimal id;

    /** 收费站hex码 */
    private String stationHex;

    /** obu名单版本 */
    private String obuVersion;

    /** 使用时间 */
    private Date applyTime;

    /** 创建或更新时间 */
    private Date createTime;

    /** cpc名单版本 */
    private String cpcVersion;

}