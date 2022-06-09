package com.pingok.vocational.domain.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 信息发布预设表 TBL_RELEASE_PRESET
 *
 * @author ruoyi
 */
@Table(name = "TBL_RELEASE_PRESET")
public class TblReleasePreset {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 信息类型 */
    @Excel(name = "信息类型")
    private Integer infoType;

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

    /** 使用状态 ：1.启用 0.停用*/
    @Excel(name = "使用状态",readConverterExp = "1=启用,2=停用")
    private Integer status;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getInfoType()
    {
        return infoType;
    }

    public void setInfoType(Integer infoType)
    {
        this.infoType = infoType;
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

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("infoType", getInfoType())
                .append("presetInfo", getPresetInfo())
                .append("typeface", getTypeface())
                .append("typefaceSize", getTypefaceSize())
                .append("color", getColor())
                .append("pictureType", getPictureType())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
