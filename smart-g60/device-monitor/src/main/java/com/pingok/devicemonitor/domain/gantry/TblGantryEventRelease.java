package com.pingok.devicemonitor.domain.gantry;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "TBL_GANTRY_EVENT_RELEASE")
@Data
public class TblGantryEventRelease implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;

    /**
     * 门架编号
     */
    private String gantryId;

    /**
     * OBU延迟时间
     */
    private Integer obuDelay;

    /**
     * 桩号
     */
    private String stakeNum;

    /**
     * 方向:1-上行  2-下行
     */
    private Integer direction;

    /**
     * 向前播报范围
     */
    private Integer broadcastRange;

    /**
     * 事件播报类型:01-蜂鸣播报模式  02-事件编码模式发布  03-事件透传信息模式发布
     */
    private String eventType;

    /**
     * 事件类型编号
     */
    private Integer eventId;

    /**
     * 事件位置
     */
    private Integer eventPosition;

    /**
     * 事件距离
     */
    private Integer eventDiscount;

    /**
     * 事件消息内容 最多30个字
     */
    private String eventInfo;

    /**
     * 播报开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date reportBeginTime;

    /**
     * 播报结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date reportEndTime;

    /**
     * 消息摘要
     */
    private String cryptoGraphicDigest;

    /**
     * 状态 0-发布失败 1-发布成功
     */
    private Integer status;

    private Long createUserId;

    private Long updateUserId;

    private Date createTime;

    private Date updateTime;

    @Transient
    private List<String> gantryIds;
}
