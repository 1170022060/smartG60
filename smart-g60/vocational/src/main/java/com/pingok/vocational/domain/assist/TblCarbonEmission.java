package com.pingok.vocational.domain.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 碳排放统计表 TBL_CARBON_EMISSION
 *
 * @author ruoyi
 */
@Table(name = "TBL_CARBON_EMISSION")
public class TblCarbonEmission {
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

    /** 类型：1.日碳排放量汇总 2.碳排放量汇总*/
    @Excel(name = "类型",readConverterExp = "1=日碳排放量汇总,2=碳排放量汇总")
    private Integer carbonType;

    /** 星期*/
    @Excel(name = "星期")
    private Integer week;

    /** 碳排放量（Kg）*/
    @Excel(name = "碳排放量（Kg）")
    private Long dischargeAmount;

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

    public Integer getCarbonType() {
        return carbonType;
    }

    public void setCarbonType(Integer carbonType) {
        this.carbonType = carbonType;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Long getDischargeAmount()
    {
        return dischargeAmount;
    }

    public void setDischargeAmount(Long dischargeAmount)
    {
        this.dischargeAmount = dischargeAmount;
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
                .append("carbonType", getCarbonType())
                .append("week", getWeek())
                .append("dischargeAmount", getDischargeAmount())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
