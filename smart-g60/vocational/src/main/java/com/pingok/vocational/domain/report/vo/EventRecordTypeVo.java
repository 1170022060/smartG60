package com.pingok.vocational.domain.report.vo;

import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * 事件记录表(按类型导出)
 *
 * @author ruoyi
 */
public class EventRecordTypeVo implements Serializable {

    /** 事件类型 */
    @Excel(name = "事件类型")
    private String eventType;

    /** 统计数量 */
    @Excel(name = "统计数量")
    private Integer count;

    /** 统计时间 */
    @Excel(name = "统计时间")
    private String time;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("eventType", getEventType())
                .append("count", getCount())
                .append("time", getTime())
                .toString();
    }
}
