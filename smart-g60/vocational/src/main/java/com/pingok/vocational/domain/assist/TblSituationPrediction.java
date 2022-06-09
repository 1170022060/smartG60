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
 * 路段运行态势预估表 TBL_SITUATION_PREDICTION
 *
 * @author ruoyi
 */
@Table(name = "TBL_SITUATION_PREDICTION")
public class TblSituationPrediction {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Excel(name = "ID")
    @Id
    private Long id;

    /**
     * 工班日期
     */
    @Excel(name = "工班日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /** 类型：1.日交通流量汇总 2.交通流量汇总*/
    @Excel(name = "类型",readConverterExp = "1=日交通流量汇总,2=交通流量汇总")
    private Integer trafficType;

    /** 星期*/
    @Excel(name = "星期")
    private Integer week;

    /** 路段区间*/
    @Excel(name = "路段区间")
    private String sectionInterval;

    /** 交通流量*/
    @Excel(name = "交通流量")
    private Integer flow;


    /** 交通状态*/
    @Excel(name = "交通状态")
    private String status;

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

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(Integer trafficType) {
        this.trafficType = trafficType;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    @Size(min = 0, max = 20, message = "路段区间不能超过20个字符")
    public String getSectionInterval()
    {
        return sectionInterval;
    }

    public void setSectionInterval(String sectionInterval)
    {
        this.sectionInterval = sectionInterval;
    }

    public Integer getFlow() {
        return flow;
    }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    @Size(min = 0, max = 20, message = "交通状态不能超过20个字符")
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

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
                .append("workDate", getWorkDate())
                .append("trafficType", getTrafficType())
                .append("week", getWeek())
                .append("sectionInterval", getSectionInterval())
                .append("flow", getFlow())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
