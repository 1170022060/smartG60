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
 * 信息发布预设表 TBL_RELEASE_PRESET
 *
 * @author ruoyi
 */
@Data
public class TblReleasePreset {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
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

}
