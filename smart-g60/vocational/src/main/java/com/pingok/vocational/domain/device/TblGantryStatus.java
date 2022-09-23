package com.pingok.vocational.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 门架设备状态表 TBL_GANTRY_STATUS
 *
 * @author ruoyi
 */
@Table(name = "TBL_GANTRY_STATUS")
public class TblGantryStatus implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 门架设备主键ID */
    @Excel(name = "门架设备主键ID")
    private Long deviceId;

    /** 最新一次心跳时间 */
    @Excel(name = "最新一次心跳时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date time;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 状态描述 */
    @Excel(name = "状态描述")
    private String statusDesc;

    /** 未上传流水数 */
    @Excel(name = "未上传流水数")
    private Integer transactionNumber;

    /** 未上传牌识数 */
    @Excel(name = "未上传牌识数")
    private Integer travelimageNumber;

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

    public Integer getTransactionNumber()
    {
        return transactionNumber;
    }

    public void setTransactionNumber(Integer transactionNumber)
    {
        this.transactionNumber = transactionNumber;
    }

    public Integer getTravelimageNumber()
    {
        return travelimageNumber;
    }

    public void setTravelimageNumber(Integer travelimageNumber)
    {
        this.travelimageNumber = travelimageNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("deviceId",getDeviceId())
                .append("time",getTime())
                .append("status",getStatus())
                .append("statusDesc",getStatusDesc())
                .append("transactionNumber",getTransactionNumber())
                .append("travelimageNumber",getTravelimageNumber())
                .toString();
    }
}
