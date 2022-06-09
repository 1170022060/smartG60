package com.pingok.vod.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备状态表(TBL_DEVICE_STATUS)
 * 
 * @author qiumin
 * @version 1.0.0 2022-03-30
 */
@Table(name = "TBL_DEVICE_STATUS")
public class TblDeviceStatus {
    /** 版本号 */
    private static final long serialVersionUID = -8885129014001558954L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键ID */
    @Id
    private Long id;

    /** 设备ID */
    private Long deviceId;

    /** 心跳时间 */
    private Date time;

    /** 1正常  0异常 */
    private Integer status;

    /** 状态描述 */
    private String statusDesc;

    /** 状态详情 */
    private String statusDetails;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取主键ID
     * 
     * @return 主键ID
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置主键ID
     * 
     * @param id
     *          主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取设备ID
     * 
     * @return 设备ID
     */
    public Long getDeviceId() {
        return this.deviceId;
    }

    /**
     * 设置设备ID
     * 
     * @param deviceId
     *          设备ID
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取心跳时间
     * 
     * @return 心跳时间
     */
    public Date getTime() {
        return this.time;
    }

    /**
     * 设置心跳时间
     * 
     * @param time
     *          心跳时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取1正常  0异常
     * 
     * @return 1正常  0异常
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置1正常  0异常
     * 
     * @param status
     *          1正常  0异常
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取状态描述
     * 
     * @return 状态描述
     */
    public String getStatusDesc() {
        return this.statusDesc;
    }

    /**
     * 设置状态描述
     * 
     * @param statusDesc
     *          状态描述
     */
    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * 获取状态详情
     * 
     * @return 状态详情
     */
    public String getStatusDetails() {
        return this.statusDetails;
    }

    /**
     * 设置状态详情
     * 
     * @param statusDetails
     *          状态详情
     */
    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}