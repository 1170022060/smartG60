package com.pingok.devicemonitor.domain.gantry;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author 
 * 门架设备状态日志表
 */
@Table(name="TBL_GANTRY_STATUS_LOG")
@Data
public class TblGantryStatusLog implements Serializable {
    @Id
    private Long id;

    /**
     * 门架设备主键ID
     */
    private Long deviceId;

    /**
     * 心跳时间
     */
    private Date time;

    /**
     * 状态:0-异常 1-正常
     */
    private Integer status;

    /**
     * 状态描述
     */
    private String statusDesc;

    /**
     * 未上传流水数
     */
    private Short transactionNumber;

    /**
     * 未上传牌识数
     */
    private Short travelimageNumber;

    private static final long serialVersionUID = 1L;
}