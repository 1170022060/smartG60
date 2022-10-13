package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.domain.BaseEntity;
import org.apache.poi.ss.usermodel.DateUtil;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 入口流水表 TBL_EN_TRANS_年份
 *
 * @author ruoyi
 */
public class TblEnTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long recordId;

    /** GID */
    private String gid;

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 入口工班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /** 1：MTC车道入2：ETC车道入 */
    private Integer transType;

    /** 通行介质 5 ETC通行，6 CPC卡，9 纸券 */
    private Integer passType;

    /** 车道Node十六进制码 */
    private String laneHex;

    /** 车道流水号 */
    private Integer transNumber;

    /** 操作员ID */
    private Integer optId;

    /** 当前班次 1：白班 ，2：夜班 */
    private Integer shift;

    /** 读卡器/RSU国密Psam号 */
    private String psamId;

    /** 读卡器/RSU国密Psam版本 */
    private String psamVer;

    /** 读卡器/RSU国密Psam终端号 */
    private String psamTerminalNo;

    /** 入口车型 1-4：1~4型客车 11-16：1~6型货车  21-26 1~6型专项作业车 */
    private Integer vehClass;

    /** 车情 0-普通  8-军警 10-紧急  14-车队 （35号公告已定义）21-绿通车 22-联合收割机 23-抢险救灾 24-Jl类型集装箱 25-大件运输；26-应急车27-货车列车或半挂汽车列车 28- J2类型集装箱 90-放行车 91-闯关车 */
    private Integer vehStatus;

    /** 车牌 */
    private String vehPlate;

    /** 车牌颜色 2 位数字:  0-蓝色，1-黄色， 2-黑色，3-白色， 4- 渐变绿色 5- 黄绿双拼色 6- 蓝白渐变色 7- 临时牌照 11-绿色 12-红色 */
    private Integer vehColor;

    /** 货车轴数 */
    private Integer truckAxle;

    /** 货车总重 */
    private Integer truckWeight;

    /** 货车核定载重 */
    private Integer truckCapacity;

    /** 车辆整备质量 */
    private Integer vehWeight;

    /** 特情类型 89：无子表型流水 通常为0   */
    private String specialType;
    private String passId;
    /** 表名   */
    private String tableName;

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Integer getPassType() {
        return passType;
    }

    public void setPassType(Integer passType) {
        this.passType = passType;
    }

    public String getLaneHex() {
        return laneHex;
    }

    public void setLaneHex(String laneHex) {
        this.laneHex = laneHex;
    }

    public Integer getTransNumber() {
        return transNumber;
    }

    public void setTransNumber(Integer transNumber) {
        this.transNumber = transNumber;
    }

    public Integer getOptId() {
        return optId;
    }

    public void setOptId(Integer optId) {
        this.optId = optId;
    }

    public Integer getShift() {
        return shift;
    }

    public void setShift(Integer shift) {
        this.shift = shift;
    }

    public String getPsamId() {
        return psamId;
    }

    public void setPsamId(String psamId) {
        this.psamId = psamId;
    }

    public String getPsamVer() {
        return psamVer;
    }

    public void setPsamVer(String psamVer) {
        this.psamVer = psamVer;
    }

    public String getPsamTerminalNo() {
        return psamTerminalNo;
    }

    public void setPsamTerminalNo(String psamTerminalNo) {
        this.psamTerminalNo = psamTerminalNo;
    }

    public Integer getVehClass() {
        return vehClass;
    }

    public void setVehClass(Integer vehClass) {
        this.vehClass = vehClass;
    }

    public Integer getVehStatus() {
        return vehStatus;
    }

    public void setVehStatus(Integer vehStatus) {
        this.vehStatus = vehStatus;
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

    public Integer getTruckAxle() {
        return truckAxle;
    }

    public void setTruckAxle(Integer truckAxle) {
        this.truckAxle = truckAxle;
    }

    public Integer getTruckWeight() {
        return truckWeight;
    }

    public void setTruckWeight(Integer truckWeight) {
        this.truckWeight = truckWeight;
    }

    public Integer getTruckCapacity() {
        return truckCapacity;
    }

    public void setTruckCapacity(Integer truckCapacity) {
        this.truckCapacity = truckCapacity;
    }

    public Integer getVehWeight() {
        return vehWeight;
    }

    public void setVehWeight(Integer vehWeight) {
        this.vehWeight = vehWeight;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }
}
