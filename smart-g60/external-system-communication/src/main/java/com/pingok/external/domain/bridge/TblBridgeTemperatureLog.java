package com.pingok.external.domain.bridge;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 桥梁温度记录表(TBL_BRIDGE_TEMPERATURE_LOG)
 *
 * @author qiumin
 * @version 1.0.0 2022-05-25
 */
@Data
@Table(name = "TBL_BRIDGE_TEMPERATURE_LOG")
public class TblBridgeTemperatureLog implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -2553327404226585436L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 结构物id
     */
    private Long structureId;

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 时间
     */
    private Date time;

    /**
     * 温度
     */
    private BigDecimal temperature;

    /**
     * 创建时间
     */
    private Date createTime;

}