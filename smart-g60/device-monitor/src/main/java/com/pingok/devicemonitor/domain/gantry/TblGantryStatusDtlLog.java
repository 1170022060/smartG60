package com.pingok.devicemonitor.domain.gantry;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 * 门架设备状态详情日志表
 */
@Table(name="TBL_GANTRY_STATUS_DTL_LOG")
@Data
public class TblGantryStatusDtlLog implements Serializable {
    @Id
    private Long id;

    /**
     * 状态id
     */
    private Long statusId;

    /**
     * 类型：1-服务器 2-工控机 3-RSU 4-牌识 5-北斗授时 6-智能机柜 7-存储 8-全景相机 9-前端软件 10-后端软件
     */
    private Integer type;

    /**
     * 主机状态：0-异常  1-正常
     */
    private Integer mainStatus;

    /**
     * 主机状态描述
     */
    private String mainStatusDesc;

    /**
     * 备机状态：0-异常  1-正常
     */
    private Short standbyStatus;

    /**
     * 备机状态描述
     */
    private String standbyStatusDesc;

    /**
     * 主机状态详情JSON
     */
    private String mainStatusDtl;

    /**
     * 备机状态详情JSON
     */
    private String standbyStatusDtl;

    private static final long serialVersionUID = 1L;
}