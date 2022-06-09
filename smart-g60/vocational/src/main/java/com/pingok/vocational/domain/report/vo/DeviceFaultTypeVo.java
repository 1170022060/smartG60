package com.pingok.vocational.domain.report.vo;

import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 设备故障(按故障类型导出)
 *
 * @author ruoyi
 */
public class DeviceFaultTypeVo {

    /** 故障类型 */
    @Excel(name = "故障类型")
    private String faultType;

    /** 统计数量 */
    @Excel(name = "统计数量")
    private Integer count;

    /** 统计时间 */
    @Excel(name = "统计时间")
    private String time;

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
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
                .append("faultType", getFaultType())
                .append("count", getCount())
                .append("time", getTime())
                .toString();
    }
}
