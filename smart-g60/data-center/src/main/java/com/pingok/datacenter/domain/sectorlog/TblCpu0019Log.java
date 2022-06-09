package com.pingok.datacenter.domain.sectorlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * CPU 0019日志表 TBL_CPU_0019_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_CPU_0019_LOG")
public class TblCpu0019Log {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 复合消费应用标示 */
    @Excel(name = "复合消费应用标示")
    private Integer consumeSign;

    /** 记录长度 */
    @Excel(name = "记录长度")
    private Integer length;

    /** 应用锁定标示 0: 未锁定 1: 已锁定 */
    @Excel(name = "应用锁定标示 0: 未锁定 1: 已锁定")
    private Integer lockSign;

    /** 入/出口收费网络号 */
    @Excel(name = "入/出口收费网络号")
    private String net;

    /** 入/出口收费站点 */
    @Excel(name = "入/出口收费站点")
    private String station;

    /** 入/出口收费车道 */
    @Excel(name = "入/出口收费车道")
    private String lane;

    /** 入/出口时间 */
    @Excel(name = "入/出口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transTime;

    /** 车型 */
    @Excel(name = "车型")
    private Integer vehClass;

    /** 入/出口状态 */
    @Excel(name = "入/出口状态")
    private Integer transType;

    /** ETC门架编号 */
    @Excel(name = "ETC门架编号")
    private String gantryHex;

    /** 通行门架时间 */
    @Excel(name = "通行门架时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date gantryTime;

    /** 车牌 */
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车牌颜色 */
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 货车轴数 */
    @Excel(name = "货车轴数")
    private Integer truckAxle;

    /** 货车总重,单位:千克（kg） */
    @Excel(name = "货车总重,单位:千克（kg）")
    private Integer truckWeight;

    /** 车辆状态标识 */
    @Excel(name = "车辆状态标识")
    private Integer vehSign;

    /** 一类交易累计金额 */
    @Excel(name = "一类交易累计金额")
    private Integer fee;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public Integer getConsumeSign() {
        return consumeSign;
    }

    public void setConsumeSign(Integer consumeSign) {
        this.consumeSign = consumeSign;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getLockSign() {
        return lockSign;
    }

    public void setLockSign(Integer lockSign) {
        this.lockSign = lockSign;
    }

    @Size(min = 0, max = 4, message = "入/出口收费网络号长度不能超过4个字符")
    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    @Size(min = 0, max = 4, message = "入/出口收费站点长度不能超过4个字符")
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Size(min = 0, max = 2, message = "入/出口收费车道长度不能超过2个字符")
    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Integer getVehClass() {
        return vehClass;
    }

    public void setVehClass(Integer vehClass) {
        this.vehClass = vehClass;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    @Size(min = 0, max = 6, message = "ETC门架编号长度不能超过6个字符")
    public String getGantryHex() {
        return gantryHex;
    }

    public void setGantryHex(String gantryHex) {
        this.gantryHex = gantryHex;
    }

    public Date getGantryTime() {
        return gantryTime;
    }

    public void setGantryTime(Date gantryTime) {
        this.gantryTime = gantryTime;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
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

    public Integer getVehSign() {
        return vehSign;
    }

    public void setVehSign(Integer vehSign) {
        this.vehSign = vehSign;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }
}
