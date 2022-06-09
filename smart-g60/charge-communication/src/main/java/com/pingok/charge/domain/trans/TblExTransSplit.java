package com.pingok.charge.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 出口流水分省信息表 TBL_EX_TRANS_SPLIT_年份
 *
 * @author ruoyi
 */
public class TblExTransSplit implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 索引：与CPC、ETC、PAPER共用SEQ */
    private Long recordId;

    /** 省份顺序 */
    private Integer provIndex;

    /** 省份编号 */
    private String provId;

    /** 实收金额 */
    private Integer tollFee;

    /** 入口站(门架)国标码 */
    private String enPointIdGb;

    /** 出口站(门架)国标码 */
    private String exPointIdGb;

    /** 入口站(门架)名称 */
    private String enTollStationName;

    /** 出口站(门架)名称 */
    private String exTollStationName;

    /** 省域计费交易起点时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 省域计费交易终点时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exTime;

    /** 收费单元编号组合 */
    private String tollInterVals;

    /** 收费单元交易金额组合 */
    private String tollPayFee;

    /** 收费单元优惠金额组合 */
    private String tollDiscountFee;

    /** 最小费率版本号组合 */
    private String tollRateVersion;

    /** 经过收费单元时间记录组合 */
    private String tollTransTime;

    /** 表名   */
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getProvIndex() {
        return provIndex;
    }

    public void setProvIndex(Integer provIndex) {
        this.provIndex = provIndex;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public Integer getTollFee() {
        return tollFee;
    }

    public void setTollFee(Integer tollFee) {
        this.tollFee = tollFee;
    }

    public String getEnPointIdGb() {
        return enPointIdGb;
    }

    public void setEnPointIdGb(String enPointIdGb) {
        this.enPointIdGb = enPointIdGb;
    }

    public String getExPointIdGb() {
        return exPointIdGb;
    }

    public void setExPointIdGb(String exPointIdGb) {
        this.exPointIdGb = exPointIdGb;
    }

    public String getEnTollStationName() {
        return enTollStationName;
    }

    public void setEnTollStationName(String enTollStationName) {
        this.enTollStationName = enTollStationName;
    }

    public String getExTollStationName() {
        return exTollStationName;
    }

    public void setExTollStationName(String exTollStationName) {
        this.exTollStationName = exTollStationName;
    }

    public Date getEnTime() {
        return enTime;
    }

    public void setEnTime(Date enTime) {
        this.enTime = enTime;
    }

    public Date getExTime() {
        return exTime;
    }

    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }

    public String getTollInterVals() {
        return tollInterVals;
    }

    public void setTollInterVals(String tollInterVals) {
        this.tollInterVals = tollInterVals;
    }

    public String getTollPayFee() {
        return tollPayFee;
    }

    public void setTollPayFee(String tollPayFee) {
        this.tollPayFee = tollPayFee;
    }

    public String getTollDiscountFee() {
        return tollDiscountFee;
    }

    public void setTollDiscountFee(String tollDiscountFee) {
        this.tollDiscountFee = tollDiscountFee;
    }

    public String getTollRateVersion() {
        return tollRateVersion;
    }

    public void setTollRateVersion(String tollRateVersion) {
        this.tollRateVersion = tollRateVersion;
    }

    public String getTollTransTime() {
        return tollTransTime;
    }

    public void setTollTransTime(String tollTransTime) {
        this.tollTransTime = tollTransTime;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
