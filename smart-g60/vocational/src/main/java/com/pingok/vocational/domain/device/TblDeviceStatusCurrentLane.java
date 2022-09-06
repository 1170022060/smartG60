package com.pingok.vocational.domain.device;

import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.system.api.RemoteIdProducerService;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 车道设备当前状态表 TBL_DEVICE_STATUS_CURRENT_LANE
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_STATUS_CURRENT_LANE")
public class TblDeviceStatusCurrentLane implements Serializable {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private String deviceId;

    /** 最新一次心跳时间 */
    @Excel(name = "最新一次心跳时间")
    private Date time;

    /** 状态 */
    @Excel(name = "状态")
    private Byte status;

    /** 状态 */
    @Excel(name = "状态")
    private String statusDetails;

    public Long getId()
    {
        return id;
    }

    public void setId()
    {
        this.id = remoteIdProducerService.nextId();
    }

    @Size(min = 0, max = 50, message = "设备编码长度不能超过50个字符")
    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
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

    public Byte getStatus()
    {
        return status;
    }

    public void setStatus(Byte status) { this.status = status; }

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
                .append("statusDetails",getStatusDetails())
                .toString();
    }
}
