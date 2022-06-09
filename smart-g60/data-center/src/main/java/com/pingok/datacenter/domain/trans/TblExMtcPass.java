package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * MTC出口流水通行信息表 TBL_EX_MTC_PASS_年份
 *
 * @author ruoyi
 */
public class TblExMtcPass implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 索引：与CPC、ETC、PAPER共用SEQ */
    private Long recordId;

    /** CPC卡ID */
    private Long cpcCardId;

    /** CPC卡电量 */
    private Integer cpcElec;

    /** CPC卡车牌号 */
    private String cpcCardPlate;

    /** CPC卡车牌颜色 */
    private Integer cpcCardColor;

    /** CPC卡车型 */
    private Integer cpcCardVeh;

    /** CPC卡车情 */
    private Integer cpcCardVehStatus;

    /** CPC入口网络号 */
    private String cpcCardEnNet;

    /** CPC卡入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date cpcCardEnTime;

    /** CPC卡入口站 */
    private String cpcCardEnStation;

    /** CPC卡过省数量 */
    private Integer cpcCardProCount;

    /** 表名   */
    private String tableName;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Long getCpcCardId() {
        return cpcCardId;
    }

    public void setCpcCardId(Long cpcCardId) {
        this.cpcCardId = cpcCardId;
    }

    public Integer getCpcElec() {
        return cpcElec;
    }

    public void setCpcElec(Integer cpcElec) {
        this.cpcElec = cpcElec;
    }

    public String getCpcCardPlate() {
        return cpcCardPlate;
    }

    public void setCpcCardPlate(String cpcCardPlate) {
        this.cpcCardPlate = cpcCardPlate;
    }

    public Integer getCpcCardColor() {
        return cpcCardColor;
    }

    public void setCpcCardColor(Integer cpcCardColor) {
        this.cpcCardColor = cpcCardColor;
    }

    public Integer getCpcCardVeh() {
        return cpcCardVeh;
    }

    public void setCpcCardVeh(Integer cpcCardVeh) {
        this.cpcCardVeh = cpcCardVeh;
    }

    public Integer getCpcCardVehStatus() {
        return cpcCardVehStatus;
    }

    public void setCpcCardVehStatus(Integer cpcCardVehStatus) {
        this.cpcCardVehStatus = cpcCardVehStatus;
    }

    public String getCpcCardEnNet() {
        return cpcCardEnNet;
    }

    public void setCpcCardEnNet(String cpcCardEnNet) {
        this.cpcCardEnNet = cpcCardEnNet;
    }

    public Date getCpcCardEnTime() {
        return cpcCardEnTime;
    }

    public void setCpcCardEnTime(Date cpcCardEnTime) {
        this.cpcCardEnTime = cpcCardEnTime;
    }

    public String getCpcCardEnStation() {
        return cpcCardEnStation;
    }

    public void setCpcCardEnStation(String cpcCardEnStation) {
        this.cpcCardEnStation = cpcCardEnStation;
    }

    public Integer getCpcCardProCount() {
        return cpcCardProCount;
    }

    public void setCpcCardProCount(Integer cpcCardProCount) {
        this.cpcCardProCount = cpcCardProCount;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
