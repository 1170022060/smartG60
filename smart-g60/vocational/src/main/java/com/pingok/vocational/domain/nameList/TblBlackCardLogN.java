package com.pingok.vocational.domain.nameList;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 状态名单信息表(TBL_BLACK_CARD_LOG)
 * 
 * @author bianj
 * @version 1.0.0 2022-10-21
 */
@Data
@Table(name = "TBL_BLACK_CARD_LOG")
public class TblBlackCardLogN implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 2993903637927397519L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private BigDecimal id;

    /** 媒介id：obu或cpc id */
    private String cardId;

    /** 发行服务机构编码 */
    private String issuerId;

    /** 部联网中心入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;

    /** 状态类型:1位数字1-卡挂失2-无卡挂起3-无卡注销4-账户透支5-合作机构黑名单6-车型不符储值卡余额不足 */
    private BigDecimal type;

    /** 1位数字1-进入状态名单2-解除状态名单 */
    private BigDecimal status;

    /** 名单生成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date creationTime;

    /** 版本号 */
    private String version;

    /** 创建或更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String versionId;
}