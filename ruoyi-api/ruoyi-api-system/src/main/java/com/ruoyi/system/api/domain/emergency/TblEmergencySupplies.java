package com.ruoyi.system.api.domain.emergency;

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
 * 应急资源信息表 TBL_EMERGENCY_SUPPLIES
 *
 * @author ruoyi
 */
@Data
public class TblEmergencySupplies {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    private Long id;

    /** 资源类型：1.物资 2.车辆 3.专家 */
    @Excel(name = "ID",readConverterExp = "1=物资,2=车辆,3=专家")
    private Integer suppliesType;

    /** 资源名称*/
    @Excel(name = "资源名称")
    private String suppliesName;

    /** 资源数量*/
    @Excel(name = "资源数量")
    private Integer suppliesCount;

    /** 物资编号*/
    @Excel(name = "物资编号")
    private String goodsId;

    /** 规格型号*/
    @Excel(name = "规格型号")
    private String specification;

    /** 品牌*/
    @Excel(name = "品牌")
    private String brand;

    /** 厂商*/
    @Excel(name = "厂商")
    private String manufacturer;

    /** 车牌*/
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车型*/
    @Excel(name = "车型")
    private Integer vehClass;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 车辆图片*/
    @Excel(name = "车辆图片")
    private String vehPhoto;

    /** 所属场地*/
    @Excel(name = "所属场地")
    private Long fieldBelong;

    /** 专家名字*/
    @Excel(name = "专家名字")
    private String expertName;

    /** 专家联系方式*/
    @Excel(name = "专家联系方式")
    private String expertPhone;

    /** 备注*/
    @Excel(name = "备注")
    private String remark;

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
