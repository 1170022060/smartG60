package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class TblEnMtcPass implements Serializable {

    private static final long serialVersionUID = 1L;


    /** CPC卡ID */
    private Long cpcCardId;

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 索引：与CPC、ETC共用SEQ */
    private Long recordId;

    /** CPC卡电量 */
    private Integer cpcElec;

    /** 表名   */
    private String tableName;

    public Long getCpcCardId() {
        return cpcCardId;
    }

    public void setCpcCardId(Long cpcCardId) {
        this.cpcCardId = cpcCardId;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public Integer getCpcElec() {
        return cpcElec;
    }

    public void setCpcElec(Integer cpcElec) {
        this.cpcElec = cpcElec;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
