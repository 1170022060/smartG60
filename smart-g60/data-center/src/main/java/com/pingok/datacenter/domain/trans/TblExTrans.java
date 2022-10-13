package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 出口流水表 TBL_EX_TRANS_年份
 *
 * @author ruoyi
 */
public class TblExTrans implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long recordId;

    /** GID */
    private String gid;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 出口工班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /** 1：MTC出 , 2：ETC出，5: 手持机 */
    private Integer transType;

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

    /** 里程 */
    private Integer mileage;

    /** 最小里程 */
    private Integer minMileage;

    /** 最小费率版本号 */
    private String minVer;

    /** 最小费率 */
    private Integer minFee;

    /** 打印记录 */
    private Integer printFlag;

    /** 计费方式 1-按OBU内累计优惠后金额计费   2-按OBU内累计优惠前金额计费（废弃） 3-按CPC卡内累计金额计费  4-省中心在线服务计费5-部中心在线服务计费  6-按全网最小费额计费  11-开放式计费 */
    private Integer feeType;

    /** 拟合金额 */
    private Integer fitFee;

    /** 拟合金额95 */
    private Integer fitFee95;

    /** 总金额 */
    private Integer tollFee;

    /** 总金额95 */
    private Integer tollFee95;

    /** 本省金额 */
    private Integer localFee;

    /** 本省金额95 */
    private Integer localFee95;

    /** 支付方式 0免费，22储值卡，23记账卡，10支付宝，11微信，1现金，2交通卡cpu，3银联卡，5节假日免费，7未付 , 6 交通卡M1 */
    private Integer payWay;

    /** 通行介质 5 ETC通行，6 CPC卡，9 纸券 */
    private Integer passType;

    /** 出口车型 1-4：1~4型客车 11-16：1~6型货车  21-26 1~6型专项作业车 */
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

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 入口网络编号 */
    private String enNet;

    /** 入口站 */
    private String enStation;

    /** 入口车道号 */
    private String enLane;

    /** 过省数量 */
    private Integer proCount;

    /** 子表流水数量 */
    private Integer subCount;

    /** Cpc卡ID */
    private String cardId;

    /** 交易金额 */
    private Long amount;

    /** 特情类型 89：无子表型流水 通常为0   */
    private String specialType;

    /** 备用字段 */
    private String remark;

    /** 备用字段 */
    private String reserve;
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

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public Integer getMinMileage() {
        return minMileage;
    }

    public void setMinMileage(Integer minMileage) {
        this.minMileage = minMileage;
    }

    public String getMinVer() {
        return minVer;
    }

    public void setMinVer(String minVer) {
        this.minVer = minVer;
    }

    public Integer getMinFee() {
        return minFee;
    }

    public void setMinFee(Integer minFee) {
        this.minFee = minFee;
    }

    public Integer getPrintFlag() {
        return printFlag;
    }

    public void setPrintFlag(Integer printFlag) {
        this.printFlag = printFlag;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
    }

    public Integer getFitFee() {
        return fitFee;
    }

    public void setFitFee(Integer fitFee) {
        this.fitFee = fitFee;
    }

    public Integer getFitFee95() {
        return fitFee95;
    }

    public void setFitFee95(Integer fitFee95) {
        this.fitFee95 = fitFee95;
    }

    public Integer getTollFee() {
        return tollFee;
    }

    public void setTollFee(Integer tollFee) {
        this.tollFee = tollFee;
    }

    public Integer getTollFee95() {
        return tollFee95;
    }

    public void setTollFee95(Integer tollFee95) {
        this.tollFee95 = tollFee95;
    }

    public Integer getLocalFee() {
        return localFee;
    }

    public void setLocalFee(Integer localFee) {
        this.localFee = localFee;
    }

    public Integer getLocalFee95() {
        return localFee95;
    }

    public void setLocalFee95(Integer localFee95) {
        this.localFee95 = localFee95;
    }

    public Integer getPayWay() {
        return payWay;
    }

    public void setPayWay(Integer payWay) {
        this.payWay = payWay;
    }

    public Integer getPassType() {
        return passType;
    }

    public void setPassType(Integer passType) {
        this.passType = passType;
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

    public Date getEnTime() {
        return enTime;
    }

    public void setEnTime(Date enTime) {
        this.enTime = enTime;
    }

    public String getEnNet() {
        return enNet;
    }

    public void setEnNet(String enNet) {
        this.enNet = enNet;
    }

    public String getEnStation() {
        return enStation;
    }

    public void setEnStation(String enStation) {
        this.enStation = enStation;
    }

    public String getEnLane() {
        return enLane;
    }

    public void setEnLane(String enLane) {
        this.enLane = enLane;
    }

    public Integer getProCount() {
        return proCount;
    }

    public void setProCount(Integer proCount) {
        this.proCount = proCount;
    }

    public Integer getSubCount() {
        return subCount;
    }

    public void setSubCount(Integer subCount) {
        this.subCount = subCount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getSpecialType() {
        return specialType;
    }

    public void setSpecialType(String specialType) {
        this.specialType = specialType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReserve() {
        return reserve;
    }

    public void setReserve(String reserve) {
        this.reserve = reserve;
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
