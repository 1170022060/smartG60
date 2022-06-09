package com.pingok.vocational.domain.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 通行费预测记录表 TBL_TOLL_FORECAST_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_TOLL_FORECAST_RECORD")
public class TblTollForecastRecord {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 预测类型：1.手动 2.自动*/
    @Excel(name = "预测类型",readConverterExp = "1=手动,2=自动")
    private Integer forecastType;

    /** 车流量预测总数（类型为1时手动输入）*/
    @Excel(name = "车流量预测总数")
    private Long forecastFlow;

    /** 通行费预测总数*/
    @Excel(name = "通行费预测总数")
    private Long forecastFee;

    /** 预测日期*/
    @Excel(name = "预测日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastDate;

    /** 星期*/
    @Excel(name = "星期")
    private Integer week;

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

    public Integer getForecastType() {
        return forecastType;
    }

    public void setForecastType(Integer forecastType) {
        this.forecastType = forecastType;
    }

    public Long getForecastFlow()
    {
        return forecastFlow;
    }

    public void setForecastFlow(Long forecastFlow)
    {
        this.forecastFlow = forecastFlow;
    }

    public Long getForecastFee()
    {
        return forecastFee;
    }

    public void setForecastFee(Long forecastFee)
    {
        this.forecastFee = forecastFee;
    }

    public Date getForecastDate()
    {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate)
    {
        this.forecastDate = forecastDate;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
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
                .append("forecastType" ,getForecastType())
                .append("forecastFlow" ,getForecastFlow())
                .append("forecastFee" ,getForecastFee())
                .append("forecastDate" ,getForecastDate())
                .append("week" ,getWeek())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }

}
