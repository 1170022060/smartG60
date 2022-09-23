package com.pingok.vocational.domain.report;

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
 * 路段行程时间表 TBL_ROAD_TRIP_TIME
 *
 * @author ruoyi
 */
@Table(name = "TBL_ROAD_TRIP_TIME")
public class TblRoadTripTime implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 日期 */
    @Excel(name = "日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actualDate;

    /**
     * 入口收费站ID
     */
    @Excel(name = "入口收费站ID")
    private String enStationId;

    /**
     * 出口收费站ID
     */
    @Excel(name = "出口收费站ID")
    private String exStationId;

    /**
     * 小时
     */
    @Excel(name = "小时")
    private Integer hour;

    /**
     * 行程时间（分钟）
     */
    @Excel(name = "行程时间（分钟）")
    private Integer tripTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    @Size(min = 0, max = 4, message = "入口收费站ID长度不能超过4个字符")
    public String getEnStationIdStationId() {
        return enStationId;
    }

    public void setEnStationIdStationId(String enStationId) {
        this.enStationId = enStationId;
    }

    @Size(min = 0, max = 4, message = "出口收费站ID长度不能超过4个字符")
    public String getExStationIdStationId() {
        return exStationId;
    }

    public void setExStationIdStationId(String exStationId) {
        this.exStationId = exStationId;
    }

    public Integer getHour() { return hour; }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getTripTime() {
        return tripTime;
    }

    public void setTripTime(Integer tripTime) {
        this.tripTime = tripTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("actualDate", getActualDate())
                .append("enStationId", getEnStationIdStationId())
                .append("exStationId", getExStationIdStationId())
                .append("hour", getHour())
                .append("tripTime", getTripTime())
                .toString();
    }
}
