package com.pingok.external.domain.bridge;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 桥梁温度信息表(TBL_BRIDGE_TEMPERATURE)
 * 
 * @author qiumin
 * @version 1.0.0 2022-05-25
 */
@Data
@Table(name = "TBL_BRIDGE_TEMPERATURE")
public class TblBridgeTemperature implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -5482974307615457594L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /**
     * 结构物id
     */
    private Long structureId;

    /** 设备编号 */
    private String deviceId;

    /** 时间 */
    private Date time;

    /** 温度 */
    private BigDecimal temperature;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

}