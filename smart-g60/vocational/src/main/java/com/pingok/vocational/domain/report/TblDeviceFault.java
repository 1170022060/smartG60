package com.pingok.vocational.domain.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 设备故障记录表 TBL_DEVICE_FAULT
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_FAULT")
public class TblDeviceFault implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 故障类型 */
    @Excel(name = "故障类型")
    private String faultType;

    /** 设备编码 */
    @Excel(name = "设备编码")
    private Long deviceId;

    /** 故障代码 */
    @Excel(name = "故障代码")
    private String faultId;

    /** 故障描述 */
    @Excel(name = "故障描述")
    private String faultDescription;

    /** 登记状态：1.人工 2.自动 */
    @Excel(name = "登记状态",readConverterExp = "1=人工,2=自动")
    private Integer registerType;

    /** 故障时间 */
    @Excel(name = "故障时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date faultTime;

    /** 处理状态*/
    @Excel(name = "处理状态")
    private Integer status;

    /** 处理时间 */
    @Excel(name = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 处理时间 */
    @Excel(name = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 处理用户ID */
    @Excel(name = "处理用户ID")
    private Long handleUserId;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;

    private String remark;

    /** 故障图片*/
    private String faultPhoto;

    /** 故障视频*/
    private String faultVideo;

    /** 故障图片数组*/
    private String[] faultPhotoStr;

    /** 故障视频数组*/
    private String[] faultVideoStr;


    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 50, message = "故障类型不能超过50个字符")
    public String getFaultType()
    {
        return faultType;
    }

    public void setFaultType(String faultType)
    {
        this.faultType = faultType;
    }

    public Long getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }

    @Size(min = 0, max = 50, message = "故障代码不能超过50个字符")
    public String getFaultId()
    {
        return faultId;
    }

    public void setFaultId(String faultId)
    {
        this.faultId = faultId;
    }

    @Size(min = 0, max = 255, message = "故障描述不能超过255个字符")
    public String getFaultDescription()
    {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription)
    {
        this.faultDescription = faultDescription;
    }

    public Integer getRegisterType()
    {
        return registerType;
    }

    public void setRegisterType(Integer registerType)
    {
        this.registerType = registerType;
    }

    public Date getFaultTime()
    {
        return faultTime;
    }

    public void setFaultTime(Date faultTime) { this.faultTime = faultTime; }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Date getHandleTime()
    {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) { this.handleTime = handleTime; }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime) { this.createTime = createTime; }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) { this.updateTime = updateTime; }

    public Long getHandleUserId()
    {
        return handleUserId;
    }

    public void setHandleUserId(Long handleUserId) { this.handleUserId = handleUserId; }

    public Long getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) { this.createUserId = createUserId; }

    public Long getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) { this.updateUserId = updateUserId; }

    public String getFaultPhoto() {
        return faultPhoto;
    }

    public void setFaultPhoto(String faultPhoto) {
        this.faultPhoto = faultPhoto;
    }

    public String getFaultVideo() {
        return faultVideo;
    }

    public void setFaultVideo(String faultVideo) {
        this.faultVideo = faultVideo;
    }

    public String[] getFaultPhotoStr() {
        return faultPhotoStr;
    }

    public void setFaultPhotoStr(String[] faultPhotoStr) {
        this.faultPhotoStr = faultPhotoStr;
    }

    public String[] getFaultVideoStr() {
        return faultVideoStr;
    }

    public void setFaultVideoStr(String[] faultVideoStr) {
        this.faultVideoStr = faultVideoStr;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("faultType",getFaultType())
                .append("deviceId",getDeviceId())
                .append("faultId",getFaultId())
                .append("faultDescription",getFaultDescription())
                .append("registerType",getRegisterType())
                .append("faultTime",getFaultTime())
                .append("status",getStatus())
                .append("handleTime",getHandleTime())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("handleUserId",getHandleUserId())
                .append("createUserId",getCreateUserId())
                .append("updateUserId",getUpdateUserId())
                .toString();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
