package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 路段行程时间表(导出格式)
 *
 * @author ruoyi
 */

public class RoadTripTimeVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 日期 */
    @Excel(name = "日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date actualDate;

    /**
     * 入口收费站名
     */
    @Excel(name = "入口收费站名")
    private String enStationId;

    /**
     * 出口收费站名
     */
    @Excel(name = "出口收费站名")
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


    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public String getEnStationIdStationId() {
        return enStationId;
    }

    public void setEnStationIdStationId(String enStationId) {
        this.enStationId = enStationId;
    }

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
                .append("actualDate", getActualDate())
                .append("enStationId", getEnStationIdStationId())
                .append("exStationId", getExStationIdStationId())
                .append("hour", getHour())
                .append("tripTime", getTripTime())
                .toString();
    }
}
