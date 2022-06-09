package com.pingok.monitor.domain.gantry;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 门架日交易汇总表(TBL_GANTRY_DAY_TRADING)
 *
 * @author qiumin
 * @version 1.0.0 2022-04-19
 */
@Table(name = "TBL_GANTRY_DAY_TRADING")
@Data
public class TblGantryDayTrading implements Serializable {


    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 门架主键id
     */
    private Long gantryId;

    /**
     * 统计日期(日)
     */
    private String collectDate;

    /**
     * ETC交易量
     */
    private BigDecimal etcBatchCount;

    /**
     * ETC交易额，单位：分
     */
    private BigDecimal etcPayFeeCount;

    /**
     * CPC交易量
     */
    private BigDecimal cpcBatchCount;

    /**
     * CPC交易额，单位：分
     */
    private BigDecimal cpcPayFeeCount;

    /**
     * ETC交易失败量
     */
    private BigDecimal etcBatchCountFail;

    /**
     * ETC交易失败额，单位：分
     */
    private BigDecimal etcPayFeeCountFail;

    /**
     * CPC交易失败量
     */
    private BigDecimal cpcBatchCountFail;

    /**
     * CPC交易失败额，单位：分
     */
    private BigDecimal cpcPayFeeCountFail;

    /**
     * 入库时间
     */
    private Date updateTime;

    /**
     * cpc卡交易量百分比
     */
    @Transient
    private BigDecimal cpcBatchPer;

    /**
     * etc卡交易量百分比
     */
    @Transient
    private BigDecimal etcBatchPer;

    /**
     * cpc卡交易额百分比
     */
    @Transient
    private BigDecimal cpcPayFeePer;

    /**
     * etc卡交易额百分比
     */
    @Transient
    private BigDecimal etcPayFeePer;

    /**
     * etc卡交易成功率
     */
    @Transient
    private BigDecimal etcPaySuccessPer;

    /**
     * cpc卡交易成功率
     */
    @Transient
    private BigDecimal cpcPaySuccessPer;

    public TblGantryDayTrading(Long id, Long gantryId, String collectDate, BigDecimal etcBatchCount, BigDecimal etcPayFeeCount, BigDecimal cpcBatchCount, BigDecimal cpcPayFeeCount, BigDecimal etcBatchCountFail, BigDecimal etcPayFeeCountFail, BigDecimal cpcBatchCountFail, BigDecimal cpcPayFeeCountFail, Date updateTime) {
        this.id = id;
        this.gantryId = gantryId;
        this.collectDate = collectDate;
        this.etcBatchCount = etcBatchCount;
        this.etcPayFeeCount = etcPayFeeCount;
        this.cpcBatchCount = cpcBatchCount;
        this.cpcPayFeeCount = cpcPayFeeCount;
        this.etcBatchCountFail = etcBatchCountFail;
        this.etcPayFeeCountFail = etcPayFeeCountFail;
        this.cpcBatchCountFail = cpcBatchCountFail;
        this.cpcPayFeeCountFail = cpcPayFeeCountFail;
        this.updateTime = updateTime;
        if (etcBatchCount.add(cpcBatchCount).doubleValue() > 0) {
            this.cpcBatchPer = cpcBatchCount.divide(etcBatchCount.add(cpcBatchCount)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            this.etcBatchPer = etcBatchCount.divide(etcBatchCount.add(cpcBatchCount)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }else {
            this.cpcBatchPer = new BigDecimal(0);
            this.etcBatchPer = new BigDecimal(0);
        }
        if (etcPayFeeCount.add(cpcPayFeeCount).doubleValue() > 0) {
            this.cpcPayFeePer = cpcPayFeeCount.divide(etcPayFeeCount.add(cpcPayFeeCount)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            this.etcPayFeePer = etcPayFeeCount.divide(etcPayFeeCount.add(cpcPayFeeCount)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }else {
            this.cpcPayFeePer = new BigDecimal(0);
            this.etcPayFeePer = new BigDecimal(0);
        }
        if (etcBatchCount.add(etcBatchCountFail).doubleValue() > 0) {
            this.etcPaySuccessPer = etcBatchCount.divide(etcBatchCount.add(etcBatchCountFail)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }else {
            this.etcPaySuccessPer = new BigDecimal(0);
        }
        if (cpcBatchCount.add(cpcBatchCountFail).doubleValue() > 0) {
            this.cpcPaySuccessPer = cpcBatchCount.divide(cpcBatchCount.add(cpcBatchCountFail)).setScale(4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
        }else {
            this.cpcPaySuccessPer = new BigDecimal(0);
        }
    }
}