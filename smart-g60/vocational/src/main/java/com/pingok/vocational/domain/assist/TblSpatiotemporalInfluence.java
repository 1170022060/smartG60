package com.pingok.vocational.domain.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 路段时空影响预估表 TBL_SPATIOTEMPORAL_INFLUENCE
 *
 * @author ruoyi
 */
@Table(name = "TBL_SPATIOTEMPORAL_INFLUENCE")
public class TblSpatiotemporalInfluence {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 事件ID*/
    @Excel(name = "事件ID")
    private Long eventId;

    /** 预计影响范围*/
    @Excel(name = "预计影响范围")
    private String incidence;

    /** 影响方式*/
    @Excel(name = "影响方式")
    private Integer influenceMode;

    /** 持续时间（分钟）*/
    @Excel(name = "持续时间（分钟）")
    private Integer duration;

    /** 危害程度*/
    @Excel(name = "危害程度")
    private Integer harmDegree;

    /** 预计解除时间*/
    @Excel(name = "预计解除时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date releaseTime;

    /**
     * 创建时间
     */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建用户Id
     */
    @Excel(name = "创建用户Id")
    private Long createUserId;

    /**
     * 更新用户Id
     */
    @Excel(name = "更新用户Id")
    private Long updateUserId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getEventId()
    {
        return eventId;
    }

    public void setEventId(Long eventId)
    {
        this.eventId = eventId;
    }

    @Size(min = 0, max = 100, message = "路段区间不能超过100个字符")
    public String getIncidence()
    {
        return incidence;
    }

    public void setIncidence(String incidence)
    {
        this.incidence = incidence;
    }
    public Integer getInfluenceMode() {
        return influenceMode;
    }

    public void setInfluenceMode(Integer influenceMode) {
        this.influenceMode = influenceMode;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getHarmDegree() {
        return harmDegree;
    }

    public void setHarmDegree(Integer harmDegree) {
        this.harmDegree = harmDegree;
    }

    public Date getReleaseTime()
    {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) { this.releaseTime = releaseTime; }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) { this.createUserId = createUserId; }

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) { this.updateUserId = updateUserId; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("eventId", getEventId())
                .append("incidence", getIncidence())
                .append("influenceMode", getInfluenceMode())
                .append("duration", getDuration())
                .append("harmDegree", getHarmDegree())
                .append("releaseTime", getReleaseTime())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
