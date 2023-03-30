package com.pingok.monitor.domain.device;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 * 设备状态表
 */
@Data
public class TblDeviceStatus implements Serializable {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 设备ID
     */
    private Long deviceId;

    /**
     * 心跳时间
     */
    private Date time;

    /**
     * 1正常  0异常
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 状态详情
     */
    private String statusDetails;
    /**
     * 故障类型
     */
    @Transient
    private String faultType;


    private static final long serialVersionUID = 1L;

}