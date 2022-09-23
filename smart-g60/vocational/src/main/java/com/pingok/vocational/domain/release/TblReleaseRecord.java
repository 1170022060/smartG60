package com.pingok.vocational.domain.release;

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
 * 信息发布记录表 TBL_RELEASE_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_RELEASE_RECORD")
public class TblReleaseRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 设备Id */
    @Excel(name = "设备Id")
    private Long deviceId;

    /** 信息类型 */
    @Excel(name = "信息类型")
    private Integer infoType;

    /** 预设名称 */
    @Excel(name = "预设名称")
    private String presetName;

    /** 预设信息 */
    @Excel(name = "预设信息")
    private String presetInfo;

    /** 内容字体 */
    @Excel(name = "内容字体")
    private String typeface;

    /** 字体大小 */
    @Excel(name = "字体大小")
    private Integer typefaceSize;

    /** 字体颜色 */
    @Excel(name = "字体颜色")
    private String color;

    /** 图片类别 */
    @Excel(name = "图片类别")
    private Integer pictureType;

    /** 发布时间 */
    @Excel(name = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date presetTime;

    /** 撤销时间 */
    @Excel(name = "撤销时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date revokeTime;

    /** 发布用户ID */
    @Excel(name = "发布用户ID")
    private Long presetUserId;

    /** 撤销用户ID */
    @Excel(name = "撤销用户ID")
    private Long revokeUserId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 100, message = "设备编码长度不能超过100个字符")
    public Long getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(Long deviceId)
    {
        this.deviceId = deviceId;
    }


    public Integer getInfoType()
    {
        return infoType;
    }

    public void setInfoType(Integer infoType)
    {
        this.infoType = infoType;
    }

    @Size(min = 0, max = 48, message = "预设名称不能超过48个字符")
    public String getPresetName() {
        return presetName;
    }

    public void setPresetName(String presetName) {
        this.presetName = presetName;
    }
    @Size(min = 0, max = 100, message = "预设信息不能超过100个字符")
    public String getPresetInfo()
    {
        return presetInfo;
    }

    public void setPresetInfo(String presetInfo)
    {
        this.presetInfo = presetInfo;
    }

    public String getTypeface()
    {
        return typeface;
    }

    public void setTypeface(String typeface)
    {
        this.typeface = typeface;
    }

    public Integer getTypefaceSize()
    {
        return typefaceSize;
    }

    public void setTypefaceSize(Integer typefaceSize)
    {
        this.typefaceSize = typefaceSize;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public Integer getPictureType()
    {
        return pictureType;
    }

    public void setPictureType(Integer pictureType)
    {
        this.pictureType = pictureType;
    }

    public Date getPresetTime()
    {
        return presetTime;
    }

    public void setPresetTime(Date presetTime) { this.presetTime = presetTime; }

    public Date getRevokeTime()
    {
        return revokeTime;
    }

    public void setRevokeTime(Date revokeTime) { this.revokeTime = revokeTime; }

    public Long getPresetUserId()
    {
        return presetUserId;
    }

    public void setPresetUserId(Long presetUserId) { this.presetUserId = presetUserId; }

    public Long getRevokeUserId()
    {
        return revokeUserId;
    }

    public void setRevokeUserId(Long revokeUserId) { this.revokeUserId = revokeUserId; }
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("deviceId",getDeviceId())
                .append("infoType", getInfoType())
                .append("presetName", getPresetName())
                .append("presetInfo", getPresetInfo())
                .append("typeface", getTypeface())
                .append("typefaceSize", getTypefaceSize())
                .append("color", getColor())
                .append("pictureType", getPictureType())
                .append("presetTime", getPresetTime())
                .append("revokeTime", getRevokeTime())
                .append("presetUserId", getPresetUserId())
                .append("revokeUserId", getRevokeUserId())
                .toString();
    }
}
