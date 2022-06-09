package com.pingok.datacenter.domain.trans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import java.util.Date;

/**
 * 流水汇总表 TBL_TRANS_SUMMARY
 *
 * @author ruoyi
 */
public class TblTransSummary {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 通行标识ID */
    private String passId;

    /** 入口GID */
    private String enGid;

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTransTime;

    /** 入口工班日期 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enWorkDate;

    /** 1：MTC车道入2：ETC车道入 */
    private Integer enTransType;

    /** 入口通行介质 5 ETC通行，6 CPC卡，9 纸券 */
    private Integer enPassType;

    /** 入口车道Node十六进制码 */
    private String enLaneHex;

    /** 入口车道流水号 */
    private Integer enTransNumber;

    /** 入口操作员ID */
    private Integer enOptId;

    /** 入口班次 1：白班 ，2：夜班 */
    private Integer enShift;

    /** 入口车型 1-4：1~4型客车 11-16：1~6型货车  21-26 1~6型专项作业车 */
    private Integer enVehClass;

    /** 入口车情 0-普通  8-军警 10-紧急  14-车队 （35号公告已定义）21-绿通车 22-联合收割机 23-抢险救灾 24-Jl类型集装箱 25-大件运输；26-应急车27-货车列车或半挂汽车列车 28- J2类型集装箱 90-放行车 91-闯关车 */
    private Integer enVehStatus;

    /** 入口车牌 */
    private String enVehPlate;

    /** 入口车牌颜色 2 位数字:  0-蓝色，1-黄色， 2-黑色，3-白色， 4- 渐变绿色 5- 黄绿双拼色 6- 蓝白渐变色 7- 临时牌照 11-绿色 12-红色 */
    private Integer enVehColor;

    /** 入口CPC/ETC卡ID */
    private String enCardId;

    /** 出口GID */
    private String exGid;

    /** 出口时间 */
    private Date exTransTime;

    /** 出口工班日期 */
    private Date exWorkDate;

    /** 1：MTC出 , 2：ETC出 */
    private Integer exTransType;

    /** 出口车道Node十六进制码 */
    private String exLaneHex;

    /** 出口车道流水号 */
    private Integer exTransNumber;

    /** 出口操作员ID */
    private Integer exOptId;

    /** 出口班次 1：白班 ，2：夜班 */
    private Integer exShift;

    /** 计费方式 1-按OBU内累计优惠后金额计费   2-按OBU内累计优惠前金额计费（废弃） 3-按CPC卡内累计金额计费  4-省中心在线服务计费5-部中心在线服务计费  6-按全网最小费额计费  11-开放式计费 */
    private Integer feeType;

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

    /** 出口通行介质 5 ETC通行，6 CPC卡，9 纸券 */
    private Integer exPassType;

    /** 出口车型 1-4：1~4型客车 11-16：1~6型货车  21-26 1~6型专项作业车 */
    private Integer exVehClass;

    /** 出口车情 0-普通  8-军警 10-紧急  14-车队 （35号公告已定义）21-绿通车 22-联合收割机 23-抢险救灾 24-Jl类型集装箱 25-大件运输；26-应急车27-货车列车或半挂汽车列车 28- J2类型集装箱 90-放行车 91-闯关车 */
    private Integer exVehStatus;

    /** 出口车牌 */
    private String exVehPlate;

    /** 出口车牌颜色 2 位数字:  0-蓝色，1-黄色， 2-黑色，3-白色， 4- 渐变绿色 5- 黄绿双拼色 6- 蓝白渐变色 7- 临时牌照 11-绿色 12-红色 */
    private Integer exVehColor;

    /** 出口CPC/ETC卡ID */
    private String exCardId;

    /** 交易金额 */
    private Long amount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getEnGid() {
        return enGid;
    }

    public void setEnGid(String enGid) {
        this.enGid = enGid;
    }

    public Date getEnTransTime() {
        return enTransTime;
    }

    public void setEnTransTime(Date enTransTime) {
        this.enTransTime = enTransTime;
    }

    public Date getEnWorkDate() {
        return enWorkDate;
    }

    public void setEnWorkDate(Date enWorkDate) {
        this.enWorkDate = enWorkDate;
    }

    public Integer getEnTransType() {
        return enTransType;
    }

    public void setEnTransType(Integer enTransType) {
        this.enTransType = enTransType;
    }

    public Integer getEnPassType() {
        return enPassType;
    }

    public void setEnPassType(Integer enPassType) {
        this.enPassType = enPassType;
    }

    public String getEnLaneHex() {
        return enLaneHex;
    }

    public void setEnLaneHex(String enLaneHex) {
        this.enLaneHex = enLaneHex;
    }

    public Integer getEnTransNumber() {
        return enTransNumber;
    }

    public void setEnTransNumber(Integer enTransNumber) {
        this.enTransNumber = enTransNumber;
    }

    public Integer getEnOptId() {
        return enOptId;
    }

    public void setEnOptId(Integer enOptId) {
        this.enOptId = enOptId;
    }

    public Integer getEnShift() {
        return enShift;
    }

    public void setEnShift(Integer enShift) {
        this.enShift = enShift;
    }

    public Integer getEnVehClass() {
        return enVehClass;
    }

    public void setEnVehClass(Integer enVehClass) {
        this.enVehClass = enVehClass;
    }

    public Integer getEnVehStatus() {
        return enVehStatus;
    }

    public void setEnVehStatus(Integer enVehStatus) {
        this.enVehStatus = enVehStatus;
    }

    public String getEnVehPlate() {
        return enVehPlate;
    }

    public void setEnVehPlate(String enVehPlate) {
        this.enVehPlate = enVehPlate;
    }

    public Integer getEnVehColor() {
        return enVehColor;
    }

    public void setEnVehColor(Integer enVehColor) {
        this.enVehColor = enVehColor;
    }

    public String getEnCardId() {
        return enCardId;
    }

    public void setEnCardId(String enCardId) {
        this.enCardId = enCardId;
    }

    public String getExGid() {
        return exGid;
    }

    public void setExGid(String exGid) {
        this.exGid = exGid;
    }

    public Date getExTransTime() {
        return exTransTime;
    }

    public void setExTransTime(Date exTransTime) {
        this.exTransTime = exTransTime;
    }

    public Date getExWorkDate() {
        return exWorkDate;
    }

    public void setExWorkDate(Date exWorkDate) {
        this.exWorkDate = exWorkDate;
    }

    public Integer getExTransType() {
        return exTransType;
    }

    public void setExTransType(Integer exTransType) {
        this.exTransType = exTransType;
    }

    public String getExLaneHex() {
        return exLaneHex;
    }

    public void setExLaneHex(String exLaneHex) {
        this.exLaneHex = exLaneHex;
    }

    public Integer getExTransNumber() {
        return exTransNumber;
    }

    public void setExTransNumber(Integer exTransNumber) {
        this.exTransNumber = exTransNumber;
    }

    public Integer getExOptId() {
        return exOptId;
    }

    public void setExOptId(Integer exOptId) {
        this.exOptId = exOptId;
    }

    public Integer getExShift() {
        return exShift;
    }

    public void setExShift(Integer exShift) {
        this.exShift = exShift;
    }

    public Integer getFeeType() {
        return feeType;
    }

    public void setFeeType(Integer feeType) {
        this.feeType = feeType;
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

    public Integer getExPassType() {
        return exPassType;
    }

    public void setExPassType(Integer exPassType) {
        this.exPassType = exPassType;
    }

    public Integer getExVehClass() {
        return exVehClass;
    }

    public void setExVehClass(Integer exVehClass) {
        this.exVehClass = exVehClass;
    }

    public Integer getExVehStatus() {
        return exVehStatus;
    }

    public void setExVehStatus(Integer exVehStatus) {
        this.exVehStatus = exVehStatus;
    }

    public String getExVehPlate() {
        return exVehPlate;
    }

    public void setExVehPlate(String exVehPlate) {
        this.exVehPlate = exVehPlate;
    }

    public Integer getExVehColor() {
        return exVehColor;
    }

    public void setExVehColor(Integer exVehColor) {
        this.exVehColor = exVehColor;
    }

    public String getExCardId() {
        return exCardId;
    }

    public void setExCardId(String exCardId) {
        this.exCardId = exCardId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
