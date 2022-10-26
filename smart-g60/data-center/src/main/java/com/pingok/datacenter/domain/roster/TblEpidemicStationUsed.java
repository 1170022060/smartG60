package com.pingok.datacenter.domain.roster;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 中高风险地区名单版本表(TBL_EPIDEMIC_STATION_USED)
 *
 * @author xia
 * @version 1.0.0 2022-07-20
 */
@Data
@Table(name = "TBL_EPIDEMIC_STATION_USED")
public class TblEpidemicStationUsed implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

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
     * 名单版本
     */
    @ApiModelProperty(value = "名单版本")
    private String version;

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
