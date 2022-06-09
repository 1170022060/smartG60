package com.pingok.external.domain.amap;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * TBL_AUTO_NAVI_MAP_RECORD
 * @author 
 */
@Data
@Table(name = "TBL_AUTO_NAVI_MAP_RECORD")
public class TblAutoNaviMapRecord implements Serializable {

    /** keyId */
    @Id
    private Long keyId;

    /** 授权城市ADCODE，例如310100 */
    private String adcode;

    /** 授权⾼德开放平台WEB服务序列号 */
    private String clientKey;

    /** 时间戳，1621243952 单位秒 */
    private Integer timestamp;

    /** 鉴权动态密钥 */
    private String digest;

    /** 事件源ID，由⾼德分配 */
    private String sourceId;

    /** 事件id */
    private Long id;

    /** 0-新增 1-更新 2-删除。 当为2删除时，只需要sourceId、id字段即可 */
    private Integer stateFlag;

    /** 事件类型 */
    private Integer type;

    /** 2:坐标 1:桩号 4:收费站 */
    private Integer locType;

    /** 道路名称，例如：G6京藏⾼速 */
    private String roadName;

    /** ⽅向， ⾼速：上⾏、下⾏、双向、[城市名]⽅向 其他：东向⻄、⻄向东、南向北、北向南 */
    private String direction;

    /** 位置信息，和locType对应 【坐标】 单点：[["110.1,39.1"]] 多点：[["110.1,39.1","110.2,39.2"]] 注：单点需要填写道路名称和⽅向 【⾥程桩】 单桩号：[["K123+133"]] 多桩号：[["K123+133","K143+332"]] 注：单桩号需要填写道路名称和⽅向 【收费站信息】 出⼊⼝封闭：[["清河收费站"]] 仅⼊⼝封闭：[["清河收费站⼊⼝"]] 仅出⼝封闭：[["清河收费站出⼝"]] 注：仅⽀持⾼速收费站 */
    private String locs;

    /** 影响等级 0-默认、1-轻微、2-⼀般、3-重⼤、4-特⼤ */
    private Integer level;

    /** ⻋道位置，可⽀持多个，逗号分隔 0-⽆、1-左侧⼀⻋道、2-左侧⼆⻋道、3-左侧三⻋道、4- 左侧四⻋道、5-左侧五⻋道、6-左侧六⻋道、7-左侧七⻋ 道、11-硬路肩、12-匝道、13-紧急停⻋带、 14-应急⻋道、 15左侧⻋道、16、中间⻋ 道、17、右侧⻋道 */
    private String lanes;

    /** 开始时间 */
    private Date startDate;

    /** 结束时间 */
    private Date endDate;

    /** 事件描述 */
    private String desc;

    /** 回调地址 */
    private String callback;

    /** 公告事件码，type为901时，需要填写 */
    private String noticeCode;

    /** 播报事件码，type为910时，需要填写 */
    private String broadcastCode;

    /** 图⽚http地址 */
    private String picUrl;

    /** 0 新增  1 审核通过、2 未通过 */
    private Integer status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    private static final long serialVersionUID = 1L;

}