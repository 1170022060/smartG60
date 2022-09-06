package com.pingok.vocational.domain.report.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 车辆OD统计记录表(分车型导出格式)
 *
 * @author ruoyi
 */

public class OdRecordVelVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 工班日期
     */
    @Excel(name = "工班日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date workDate;

    /**
     * 小时
     */
    @Excel(name = "小时")
    private Integer hour;

    /**
     * 车型
     */
    @Excel(name = "车型")
    private String vehClass;

    /**
     * 流量
     */
    @Excel(name = "流量")
    private Integer flow;

    public Date getWorkDate() {
        return workDate;
    }

    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Integer getHour() { return hour; }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public String getVehClass() { return vehClass; }

    public void setVehClass(String vehClass) {
        this.vehClass = vehClass;
    }

    public Integer getFlow() { return flow; }

    public void setFlow(Integer flow) {
        this.flow = flow;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workDate", getWorkDate())
                .append("hour", getHour())
                .append("vehClass", getVehClass())
                .append("flow", getFlow())
                .toString();
    }

}
