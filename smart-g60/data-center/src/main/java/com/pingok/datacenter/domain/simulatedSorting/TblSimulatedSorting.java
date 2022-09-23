package com.pingok.datacenter.domain.simulatedSorting;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 模拟清分表(TBL_SIMULATED_SORTING)
 *
 * @author bianj
 * @version 1.0.0 2022-09-08
 */
@Data
@Table(name = "TBL_SIMULATED_SORTING")
public class TblSimulatedSorting implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5106253586298500991L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 清分日
     */
    private String sortingDate;

    /**
     * 小时
     */
    private String sortingHour;

    /**
     * 通行介质：1-OBU（单片式、双片式） 2-CPC卡
     */
    private Integer mediaType;

    /**
     * 路径类型：1-有入口有出口 2-有入口无出口 3-无入口有出口 4-无入口无出口
     */
    private Integer routeType;

    /**
     * 卡类型：0-CPC卡   1-ETC储值卡  2-ETC记账卡
     */
    private Integer cardType;

    /**
     * 发行方：1-本省  0-外省
     */
    private Integer issueId;

    /**
     * 流量
     */
    private Integer flow;

    /**
     * 金额（分）
     */
    private Integer fee;

}