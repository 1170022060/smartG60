package com.pingok.datacenter.domain.trans;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 出入口断面记录表(TBL_SECTION_RECORD)
 * 
 * @author xia
 * @version 1.0.0 2022-06-15
 */
@Data
@Table(name = "TBL_SECTION_RECORD")
public class TblSectionRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 1L;

    /** 主键 */
    @Id
    private Long id;

    /** 工班日期 */
    private Date workDate;

    /** 收费站ID */
    private String stationId;

    /** 方向：1.入口 2.出口 */
    private Integer direction;

    /** ETC本地流量 */
    private Integer etcLocal;

    /** ETC异地流量 */
    private Integer etcElse;

    /** MTC单省流量 */
    private Integer mtcSingle;

    /** MTC跨省流量 */
    private Integer mtcTrans;

    /** 放行流量 */
    private Integer releaseTrans;

    /** 闯关流量 */
    private Integer barrierTrans;

    /** 前亭流量 */
    private Integer frontTrans;

    /** 牌识流量 */
    private Integer license;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    /** 交易金额 */
    private Integer amount;

}