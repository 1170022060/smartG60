package com.pingok.vocational.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 设备维修记录表 TBL_DEVICE_REPAIR
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_REPAIR")
public class TblDeviceRepair {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private Long deviceId;

    /** 维修时间 */
    @Excel(name = "维修时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date repairTime;

    /** 维修类型：1.维修 2.更换 3.报废 */
    @Excel(name = "维修类型",readConverterExp = "1=维修,2=更换,3=报废")
    private Integer repairType;

    /** 原因 */
    @Excel(name = "原因")
    private String reason;

    /** 维修内容 */
    @Excel(name = "维修内容")
    private String content;

    /** 负责用户ID */
    @Excel(name = "负责用户ID")
    private Long responsibleUserId;

    public Long getId()
    {
        return id;
    }

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

    public Date getRepairTime()
    {
        return repairTime;
    }

    public void setRepairTime(Date repairTime)
    {
        this.repairTime = repairTime;
    }

    public Integer getRepairType()
    {
        return repairType;
    }

    public void setRepairType(Integer repairType)
    {
        this.repairType = repairType;
    }

    public String getReason()
    {
        return reason;
    }

    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public Long getResponsibleUserId()
    {
        return responsibleUserId;
    }

    public void setResponsibleUserId(Long responsibleUserId)
    {
        this.responsibleUserId = responsibleUserId;
    }

}
