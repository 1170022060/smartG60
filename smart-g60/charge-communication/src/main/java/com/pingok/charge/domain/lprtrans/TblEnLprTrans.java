package com.pingok.charge.domain.lprtrans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 入口车牌识别流水表 TBL_EN_LPR_TRANS_年份
 *
 * @author ruoyi
 */
public class TblEnLprTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** LaneHex 6位 + yyyyMMddHH + TransNumber 6位前补0 */
    private String transId;

    /** 车道Node Hex */
    private String laneHex;

    /** 车道Node国标 */
    private String laneGb;

    /** 识别时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 车道流水号 */
    private Integer transNumber;

    /** 车牌 */
    private String vehPlate;

    /** 车牌颜色 */
    private Integer vehColor;

    /** 表名   */
    private String tableName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }

    public String getLaneHex() {
        return laneHex;
    }

    public void setLaneHex(String laneHex) {
        this.laneHex = laneHex;
    }

    public String getLaneGb() {
        return laneGb;
    }

    public void setLaneGb(String laneGb) {
        this.laneGb = laneGb;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Integer getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(Integer transNumber) {
        this.transNumber = transNumber;
    }

    public String getVehPlate() {
        return vehPlate;
    }

    public void setVehPlate(String vehPlate) {
        this.vehPlate = vehPlate;
    }

    public Integer getVehColor() {
        return vehColor;
    }

    public void setVehColor(Integer vehColor) {
        this.vehColor = vehColor;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
