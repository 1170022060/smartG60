package com.pingok.external.domain.primary;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 按治超站统计车货总重大于 100 吨超限量信息表(TBL_TOTAL_WEIGHT_OVER100_INFO)
 * 
 * @author bianj
 * @version 1.0.0 2023-02-13
 */
@Data
@Table(name = "TBL_TOTAL_WEIGHT_OVER100_INFO")
public class TblTotalWeightOver100Info implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1966021993491138583L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 超限量 */
    private String overLoadList;

    /**  站点名 */
    private String stationName;

    /** 审核后超 限流量 */
    private String reviewFlowList;

    /** 站ID */
    private String nodeId;

    /** 日期 */
    private String time;

}