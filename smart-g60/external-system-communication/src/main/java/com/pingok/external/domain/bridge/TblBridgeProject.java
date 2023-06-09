package com.pingok.external.domain.bridge;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 桥梁项目信息表(TBL_BRIDGE_PROJECT)
 *
 * @author qiumin
 * @version 1.0.0 2022-05-26
 */
@Data
@Table(name = "TBL_BRIDGE_PROJECT")
public class TblBridgeProject implements Serializable {
    /**
     * 版本号
     */
    private static final long serialVersionUID = 1071426616690171247L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 项目编号
     */
    private String serialNumber;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目类型 1-施工监测 2-长期监测 3-应急监测
     */
    private Integer type;

    /**
     * 项目开始时间
     */
    private Date startTime;

    /**
     * 项目结束时间
     */
    private Date endTime;

    /**
     * 业主单位
     */
    private String ownersUnitName;

    /**
     * 业主单位联系人
     */
    private String ownersUnitLinkman;

    /**
     * 业主单位电话
     */
    private String ownersUnitPhone;

    /**
     * 业主单位通信地址
     */
    private String ownersUnitAddress;

    /**
     * 实施单位
     */
    private String implementUnitName;

    /**
     * 实施单位联系人
     */
    private String implementUnitLinkman;

    /**
     * 实施单位电话
     */
    private String implementUnitPhone;

    /**
     * 实施单位通信地址
     */
    private String implementUnitAddress;

    /**
     * 发布状态 0 未发布 1 已发布
     */
    private Integer stats;

    /**
     * 最新发布时间
     */
    private Date releaseTime;

    /**
     * 过期状态 0 未过期 1 已过期
     */
    private Integer pastDueStats;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}