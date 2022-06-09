package com.pingok.vocational.domain.parkingLot;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 停车场出入车辆信息表(TBL_PARKING_VEHICLE_INFO)
 * 
 * @author qiumin
 * @version 1.0.0 2022-04-08
 */
@Table(name = "TBL_PARKING_VEHICLE_INFO")
public class TblParkingVehicleInfo {
    /** 版本号 */
    private static final long serialVersionUID = -7132345013553295596L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 入口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 出口时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exTime;

    /** 车牌 */
    private String vehPlate;

    /** 车型 */
    private Integer vehClass;

    /** 车牌颜色 */
    private Integer vehColor;

    /** 停车区域id */
    private Long parkingId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取id
     * 
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取入口时间
     * 
     * @return 入口时间
     */
    public Date getEnTime() {
        return this.enTime;
    }

    /**
     * 设置入口时间
     * 
     * @param enTime
     *          入口时间
     */
    public void setEnTime(Date enTime) {
        this.enTime = enTime;
    }

    /**
     * 获取出口时间
     * 
     * @return 出口时间
     */
    public Date getExTime() {
        return this.exTime;
    }

    /**
     * 设置出口时间
     * 
     * @param exTime
     *          出口时间
     */
    public void setExTime(Date exTime) {
        this.exTime = exTime;
    }

    /**
     * 获取车牌
     * 
     * @return 车牌
     */
    public String getVehPlate() {
        return this.vehPlate;
    }

    /**
     * 设置车牌
     * 
     * @param vehPlate
     *          车牌
     */
    public void setVehPlate(String vehPlate) {
        this.vehPlate = vehPlate;
    }

    /**
     * 获取车型
     * 
     * @return 车型
     */
    public Integer getVehClass() {
        return this.vehClass;
    }

    /**
     * 设置车型
     * 
     * @param vehClass
     *          车型
     */
    public void setVehClass(Integer vehClass) {
        this.vehClass = vehClass;
    }

    /**
     * 获取车牌颜色
     * 
     * @return 车牌颜色
     */
    public Integer getVehColor() {
        return this.vehColor;
    }

    /**
     * 设置车牌颜色
     * 
     * @param vehColor
     *          车牌颜色
     */
    public void setVehColor(Integer vehColor) {
        this.vehColor = vehColor;
    }

    /**
     * 获取停车区域id
     * 
     * @return 停车区域id
     */
    public Long getParkingId() {
        return this.parkingId;
    }

    /**
     * 设置停车区域id
     * 
     * @param parkingId
     *          停车区域id
     */
    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime
     *          更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建用户ID
     * 
     * @return 创建用户ID
     */
    public Long getCreateUserId() {
        return this.createUserId;
    }

    /**
     * 设置创建用户ID
     * 
     * @param createUserId
     *          创建用户ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取更新用户ID
     * 
     * @return 更新用户ID
     */
    public Long getUpdateUserId() {
        return this.updateUserId;
    }

    /**
     * 设置更新用户ID
     * 
     * @param updateUserId
     *          更新用户ID
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}