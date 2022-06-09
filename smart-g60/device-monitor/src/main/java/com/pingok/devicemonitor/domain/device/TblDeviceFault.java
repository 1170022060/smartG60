package com.pingok.devicemonitor.domain.device;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;

/**
 * @author 
 * 设备故障记录表
 */
@Table(name="TBL_DEVICE_FAULT")
@Data
public class TblDeviceFault implements Serializable {
    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 故障类型
     */
    private String faultType;

    /**
     * 设备id
     */
    private Long deviceId;

    /**
     * 故障代码
     */
    private String faultId;

    /**
     * 故障描述
     */
    private String faultDescription;

    /**
     * 登记状态：1.人工 2.自动
     */
    private Integer registerType;

    /**
     * 故障时间
     */
    private Date faultTime;

    /**
     * 处理状态
     */
    private Integer status;

    /**
     * 处理时间
     */
    private Date handleTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 处理用户ID
     */
    private Long handleUserId;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    private static final long serialVersionUID = 1L;
}