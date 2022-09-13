package com.pingok.datacenter.domain.blackcard;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 状态名单表（收费站在用的）(TBL_BLACK_CARD_STATION_USED)
 *
 * @author bianj
 * @version 1.0.0 2022-09-13
 */
@Table(name = "TBL_BLACK_CARD_STATION_USED")
@Data
public class TblBlackCardStationUsed implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -1724479719672993250L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 收费站hex码
     */
    @ApiModelProperty(value = "收费站hex码")
    private String stationHex;

    /**
     * 卡名单版本
     */
    @ApiModelProperty(value = "卡名单版本")
    private String obuVersion;

    /**
     * 使用时间
     */
    @ApiModelProperty(value = "使用时间")
    private Date applyTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;



}