package com.pingok.devicemonitor.domain.device;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

/**
 * @author 
 * 设备状态日志表
 */
@Table(name="TBL_DEVICE_STATUS_LOG")
@Data
public class TblDeviceStatusLog implements Serializable {
    /**
     * 主键ID
     */
    @Id
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

    private static final long serialVersionUID = 1L;
}