package com.pingok.charge.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 纸券出口流水通行信息表 TBL_EX_PAPER_PASS_年份
 *
 * @author ruoyi
 */
public class TblExPaperPass implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 车道Node十六进制码 */
    private String laneHex;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 索引：与CPC、ETC、PAPER共用SEQ */
    private Long recordId;

    /** 纸券入口网络号 */
    private String paperEnNet;

    /** 纸券入口站 */
    private String paperEnStation;

    /** 表名   */
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLaneHex() {
        return laneHex;
    }

    public void setLaneHex(String laneHex) {
        this.laneHex = laneHex;
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

    public String getPaperEnNet() {
        return paperEnNet;
    }

    public void setPaperEnNet(String paperEnNet) {
        this.paperEnNet = paperEnNet;
    }

    public String getPaperEnStation() {
        return paperEnStation;
    }

    public void setPaperEnStation(String paperEnStation) {
        this.paperEnStation = paperEnStation;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
