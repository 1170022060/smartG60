package com.pingok.external.domain.bridge;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 桥梁预警信息表(TBL_BRIDGE_WARNING)
 *
 * @author qiumin
 * @version 1.0.0 2022-05-26
 */
@Data
@Table(name = "TBL_BRIDGE_WARNING")
public class TblBridgeWarning implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 8253097294958177898L;

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 项目id
     */
    private Long projectId;

    /**
     * 结构物id
     */
    private Long structureId;

    /**
     * 预警时间
     */
    private Date alarmTime;

    /**
     * 预警等级 1 红色预警 2 黄色预警 3 蓝色预警
     */
    private Integer alarmLevel;

    /**
     * 事件描述
     */
    private String incidentDescription;

    /**
     * 处理状况 0-未处理 1-已处理 2-计划处理
     */
    private Integer treatmentStatus;

    /**
     * 预警状况 0-未预警 1-已预警 2-不预警
     */
    private Integer alarmStatus;

    /**
     * 处理措施
     */
    private String treatmentMeasure;

    /**
     * 处理结果
     */
    private String treatmentResult;

    /**
     * 预警原因
     */
    private String alarmReason;

    /**
     * 处理时间
     */
    private Date treatmentTime;

    /**
     * 预警策略
     */
    private String alarmStrategy;

    /**
     * 处理用户
     */
    private String treatmentUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}