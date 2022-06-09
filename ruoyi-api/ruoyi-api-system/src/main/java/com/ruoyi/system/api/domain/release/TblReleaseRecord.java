package com.ruoyi.system.api.domain.release;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 信息发布记录表 TBL_RELEASE_RECORD
 *
 * @author ruoyi
 */
@Data
public class TblReleaseRecord {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    private Long id;

    /** 设备Id */
    @Excel(name = "设备Id")
    private Long deviceId;

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

}
