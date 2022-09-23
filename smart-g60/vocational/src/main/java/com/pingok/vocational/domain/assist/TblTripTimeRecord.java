package com.pingok.vocational.domain.assist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 行程时间预测记录表 TBL_TRIP_TIME_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_TRIP_TIME_RECORD")
public class TblTripTimeRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /**
     * 预测日期
     */
    @Excel(name = "预测日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date forecastDate;

    /**
     * 行程时间（分钟）
     */
    @Excel(name = "行程时间（分钟）")
    private Integer tripTime;

    /**
     * 预测路段
     */
    @Excel(name = "预测路段")
    private String forecastRoad;

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

    public Date getForecastDate() {
        return forecastDate;
    }

    public void setForecastDate(Date forecastDate) {
        this.forecastDate = forecastDate;
    }

    public Integer getTripTime() {
        return tripTime;
    }

    public void setTripTime(Integer tripTime) {
        this.tripTime = tripTime;
    }

    @Size(min = 0, max = 50, message = "预测路段长度不能超过50个字符")
    public String getForecastRoad() {
        return forecastRoad;
    }

    public void setForecastRoad(String forecastRoad) {
        this.forecastRoad = forecastRoad;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("forecastDate", getForecastDate())
                .append("tripTime", getTripTime())
                .append("forecastRoad", getForecastRoad())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
