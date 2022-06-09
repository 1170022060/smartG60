package com.pingok.vocational.domain.emergency;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * 应急资源信息表 TBL_EMERGENCY_SUPPLIES
 *
 * @author ruoyi
 */
@Table(name = "TBL_EMERGENCY_SUPPLIES")
public class TblEmergencySupplies {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
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

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getSuppliesType()
    {
        return suppliesType;
    }

    public void setSuppliesType(Integer suppliesType)
    {
        this.suppliesType = suppliesType;
    }

    @Size(min = 0, max = 20, message = "资源名称长度不能超过20个字符")
    public String getSuppliesName()
    {
        return suppliesName;
    }

    public void setSuppliesName(String suppliesName)
    {
        this.suppliesName = suppliesName;
    }

    public Integer getSuppliesCount()
    {
        return suppliesCount;
    }

    public void setSuppliesCount(Integer suppliesCount)
    {
        this.suppliesCount = suppliesCount;
    }

    @Size(min = 0, max = 50, message = "物资编号长度不能超过50个字符")
    public String getGoodsId()
    {
        return goodsId;
    }

    public void setGoodsId(String goodsId)
    {
        this.goodsId = goodsId;
    }

    @Size(min = 0, max = 255, message = "规格型号长度不能超过255个字符")
    public String getSpecification()
    {
        return specification;
    }

    public void setSpecification(String specification)
    {
        this.specification = specification;
    }

    @Size(min = 0, max = 255, message = "品牌长度不能超过255个字符")
    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    @Size(min = 0, max = 255, message = "厂商长度不能超过255个字符")
    public String getManufacturer()
    {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer)
    {
        this.manufacturer = manufacturer;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
    public String getVehPlate()
    {
        return vehPlate;
    }

    public void setVehPlate(String vehPlate)
    {
        this.vehPlate = vehPlate;
    }

    public Integer getVehClass()
    {
        return vehClass;
    }

    public void setVehClass(Integer vehClass)
    {
        this.vehClass = vehClass;
    }

    public Integer getVehColor()
    {
        return vehColor;
    }

    public void setVehColor(Integer vehColor)
    {
        this.vehColor = vehColor;
    }

    @Size(min = 0, max = 255, message = "车牌照片长度不能超过255个字符")
    public String getVehPhoto()
    {
        return vehPhoto;
    }

    public void setVehPhoto(String vehPhoto)
    {
        this.vehPhoto = vehPhoto;
    }

    public Long getFieldBelong()
    {
        return fieldBelong;
    }

    public void setFieldBelong(Long fieldBelong)
    {
        this.fieldBelong = fieldBelong;
    }

    @Size(min = 0, max = 20, message = "专家名字不能超过20个字符")
    public String getExpertName()
    {
        return expertName;
    }

    public void setExpertName(String expertName) { this.expertName = expertName; }

    @Size(min = 0, max = 20, message = "专家联系方式不能超过20个字符")
    public String getExpertPhone()
    {
        return expertPhone;
    }

    public void setExpertPhone(String expertPhone) { this.expertPhone = expertPhone; }

    @Size(min = 0, max = 100, message = "备注不能超过100个字符")
    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark) { this.remark = remark; }

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
                .append("id",getId())
                .append("suppliesType",getSuppliesType())
                .append("suppliesName",getSuppliesName())
                .append("suppliesCount",getSuppliesCount())
                .append("goodsId",getGoodsId())
                .append("specification",getSpecification())
                .append("brand",getBrand())
                .append("manufacturer",getManufacturer())
                .append("vehPlate",getVehPlate())
                .append("vehClass",getVehClass())
                .append("vehColor",getVehColor())
                .append("vehPhoto",getVehPhoto())
                .append("fieldBelong",getFieldBelong())
                .append("expertName",getExpertName())
                .append("expertPhone",getExpertPhone())
                .append("remark",getRemark())
                .append("status",getStatus())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("createUserId",getCreateUserId())
                .append("updateUserId",getUpdateUserId())
                .toString();
    }
}
