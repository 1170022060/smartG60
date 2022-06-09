package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 特情统计(导出格式)
 *
 * @author ruoyi
 */

public class SpecialRecordVo {

    private static final long serialVersionUID = 1L;
    /**
     * 工班日期
     */
    @Excel(name = "工班日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /**
     * 收费站名
     */
    @Excel(name = "收费站名")
    private String stationName;

    /**
     * 特情类型
     */
    @Excel(name = "特情类型")
    private String type;

    /** 统计数量 */
    @Excel(name = "统计数量")
    private Integer count;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workDate", getWorkDate())
                .append("stationName", getStationName())
                .append("type", getType())
                .append("count", getCount())
                .toString();
    }
}
