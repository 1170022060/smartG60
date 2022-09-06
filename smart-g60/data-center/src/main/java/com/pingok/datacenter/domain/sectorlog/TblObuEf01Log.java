package com.pingok.datacenter.domain.sectorlog;

import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * OBU EF01日志表 TBL_OBU_EF01_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_OBU_EF01_LOG")
public class TblObuEf01Log implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 车牌 */
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车牌颜色 */
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 车型 */
    @Excel(name = "车型")
    private Integer vehClass;

    /** 车辆用户类型 */
    @Excel(name = "车辆用户类型")
    private Integer vehUserType;

    /** 长（2字节）宽（1字节）高（1字节） */
    @Excel(name = "长（2字节）宽（1字节）高（1字节）")
    private String vehLwh;

    /** 车轮数 */
    @Excel(name = "车轮数")
    private Integer wheels;

    /** 车轴数 */
    @Excel(name = "车轴数")
    private Integer axle;

    /** 轴距 */
    @Excel(name = "轴距")
    private Integer wheelbase;

    /** 车辆载重/座位数 */
    @Excel(name = "车辆载重/座位数")
    private Integer loadOrSeats;

    /** 车辆特征描述 */
    @Excel(name = "车辆特征描述")
    private String vehDescribe;

    /** 车辆发动机号 */
    @Excel(name = "车辆发动机号")
    private String vehEngineNo;

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

    public Integer getVehClass() {
        return vehClass;
    }

    public void setVehClass(Integer vehClass) {
        this.vehClass = vehClass;
    }

    public Integer getVehUserType() {
        return vehUserType;
    }

    public void setVehUserType(Integer vehUserType) {
        this.vehUserType = vehUserType;
    }

    @Size(min = 0, max = 20, message = "长宽高长度不能超过20个字符")
    public String getVehLwh() {
        return vehLwh;
    }

    public void setVehLwh(String vehLwh) {
        this.vehLwh = vehLwh;
    }

    public Integer getWheels() {
        return wheels;
    }

    public void setWheels(Integer wheels) {
        this.wheels = wheels;
    }

    public Integer getAxle() {
        return axle;
    }

    public void setAxle(Integer axle) {
        this.axle = axle;
    }

    public Integer getWheelbase() {
        return wheelbase;
    }

    public void setWheelbase(Integer wheelbase) {
        this.wheelbase = wheelbase;
    }

    public Integer getLoadOrSeats() {
        return loadOrSeats;
    }

    public void setLoadOrSeats(Integer loadOrSeats) {
        this.loadOrSeats = loadOrSeats;
    }

    @Size(min = 0, max = 30, message = "车辆特征描述长度不能超过30个字符")
    public String getVehDescribe() {
        return vehDescribe;
    }

    public void setVehDescribe(String vehDescribe) {
        this.vehDescribe = vehDescribe;
    }

    @Size(min = 0, max = 50, message = "车辆发动机号长度不能超过50个字符")
    public String getVehEngineNo() {
        return vehEngineNo;
    }

    public void setVehEngineNo(String vehEngineNo) {
        this.vehEngineNo = vehEngineNo;
    }
}
