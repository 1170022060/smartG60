package com.pingok.vocational.domain.report.vo;

import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 事件记录表(按车型导出)
 *
 * @author ruoyi
 */
public class EventRecordClassVo {

    /** 车型*/
    @Excel(name = "车型")
    private String vehClass;

    /** 统计数量 */
    @Excel(name = "统计数量")
    private Integer count;

    /** 统计时间 */
    @Excel(name = "统计时间")
    private String time;

    public String getVehClass() {
        return vehClass;
    }

    public void setVehClass(String vehClass) {
        this.vehClass = vehClass;
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
                .append("vehClass", getVehClass())
                .append("count", getCount())
                .append("time", getTime())
                .toString();
    }
}
