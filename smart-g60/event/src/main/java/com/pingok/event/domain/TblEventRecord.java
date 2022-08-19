package com.pingok.event.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 事件记录表(TBL_EVENT_RECORD)
 *
 * @author qiumin
 * @version 1.0.0 2022-03-30
 */
@Data
@Table(name = "TBL_EVENT_RECORD")
public class TblEventRecord implements Serializable {

    /**
     * 主键
     */
    @Id
    private Long id;

    /**
     * 事件类型
     */
    private String eventType;

    @Transient
    private String eventTypeLabel;

    /**
     * 位置区间
     */
    private String locationInterval;

    /**
     * 车型
     */
    private Integer vehClass;

    /**
     * 车牌
     */
    private String vehPlate;

    /**
     * 车牌颜色
     */
    private Integer vehColor;

    /**
     * 事件照片
     */
    private String eventPhoto;

    /**
     * 事件发生时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    /**
     * 车速
     */
    private Integer speed;

    /**
     * 车道
     */
    private Integer lane;

    /**
     * 视频
     */
    private String video;

    /**
     * 确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 解除确认时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date relieveTime;

    /**
     * 确认用户ID
     */
    private Long confirmUserId;

    /**
     * 解除确认用户ID
     */
    private Long relieveUserId;

    /**
     * 状态 -1-误报 0-新增 1-已确认 2-已处置
     */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    private Long eventId;

    private String remark;
    /** 上行、下行、双向 */
    private String direction;

    @Transient
    private List<TblEventHandle> eventHandles;

}