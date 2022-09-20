package com.pingok.datacenter.domain.simulatedSorting.vo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 模拟清分表(TBL_SIMULATED_SORTING)
 *
 * @author bianj
 * @version 1.0.0 2022-09-08
 */
@Data
public class SimulatedSortingVo implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5106253586298500991L;

    /* This code was generated by TableGo tools, mark 1 begin. */


    /**
     * 日期
     */
    private String sortingDate;

    /**
     * 本省入本省出CPC储值卡
     */
    private BigDecimal inAndOutCpcFee;

    /**
     * 本省入本省出ETC储值卡
     */
    private BigDecimal inAndOutEtcSvFee;

    /**
     * 本省入本省出ETC记账卡
     */
    private BigDecimal inAndOutEtcCcFee;

    /**
     * 本省入CPC储值卡
     */
    private BigDecimal inCpcFee;

    /**
     * 本省入ETC储值卡
     */
    private BigDecimal inEtcSvFee;

    /**
     * 本省入ETC记账卡
     */
    private BigDecimal inEtcCcFee;

    /**
     * 本省出CPC储值卡
     */
    private BigDecimal outCpcFee;

    /**
     * 本省出ETC储值卡
     */
    private BigDecimal outEtcSvFee;

    /**
     * 本省出ETC记账卡
     */
    private BigDecimal outEtcCcFee;

    /**
     * 途径CPC储值卡
     */
    private BigDecimal passCpcFee;

    /**
     * 途径ETC储值卡
     */
    private BigDecimal passEtcSvFee;

    /**
     * 途径ETC记账卡
     */
    private BigDecimal passEtcCcFee;

    /**
     * 合计
     */
    private BigDecimal allFee;
}