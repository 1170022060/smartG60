package com.pingok.vocational.domain.device;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 设备当前状态表 TBL_DEVICE_STATUS
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_STATUS")
public class TblDeviceStatus {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private Long deviceId;

    /** 最新一次心跳时间 */
    @Excel(name = "最新一次心跳时间")
    private Date time;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 状态描述 */
    @Excel(name = "状态描述")
    private String statusDesc;

    /** 状态详情 */
    @Excel(name = "状态详情")
    private String statusDetails;

    public Long getId() { return id; }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }

    public Date getTime()
    {
        return time;
    }

    public void setTime(Date time)
    {
        this.time = time;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status) { this.status = status; }

    @Size(min = 0, max = 255, message = "状态描述长度不能超过255个字符")
    public String getStatusDesc()
    {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc)
    {
        this.statusDesc = statusDesc;
    }

    @Size(min = 0, max = 65535, message = "设备编码长度不能超过65535个字符")
    public String getStatusDetails()
    {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails)
    {
        this.statusDetails = statusDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("deviceId",getDeviceId())
                .append("time",getTime())
                .append("status",getStatus())
                .append("statusDesc",getStatusDesc())
                .append("statusDetails",getStatusDetails())
                .toString();
    }
}
