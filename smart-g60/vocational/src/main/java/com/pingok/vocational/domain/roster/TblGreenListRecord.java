package com.pingok.vocational.domain.roster;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 绿通车辆名单记录表 TBL_GREEN_LIST_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_GREEN_LIST_RECORD")
public class TblGreenListRecord
{
    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 记录生成时间 */
    @Excel(name = "记录生成时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 绿通记录ID */
    @Excel(name = "绿通记录ID")
    private String greenId;

    /** 车牌*/
    @Excel(name = "车牌")
    private String vehPlate;

    /** 车型*/
    @Excel(name = "车型")
    private Integer vehClass;

    /** 车牌颜色*/
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 提交时间 */
    @Excel(name = "提交时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;

    /** 入口省份*/
    @Excel(name = "入口省份")
    private String startDistrictId;

    /** 出口省份*/
    @Excel(name = "出口省份")
    private String endDistrictId;

    /** 入库时间 */
    @Excel(name = "入库时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date insertTime;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 11, message = "版本号不能超过11个字符")
    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    @Size(min = 0, max = 25, message = "绿通记录ID不能超过25个字符")
    public String getGreenId()
    {
        return greenId;
    }

    public void setGreenId(String greenId)
    {
        this.greenId = greenId;
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

    public Date getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    @Size(min = 0, max = 6, message = "入口省份不能超过6个字符")
    public String getStartDistrictId()
    {
        return startDistrictId;
    }

    public void setSerialVersionUID(String startDistrictId)
    {
        this.startDistrictId = startDistrictId;
    }

    @Size(min = 0, max = 6, message = "出口省份不能超过6个字符")
    public String getEndDistrictId()
    {
        return endDistrictId;
    }

    public void setEndDistrictId(String endDistrictId)
    {
        this.endDistrictId = endDistrictId;
    }

    public Date getInsertTime()
    {
        return insertTime;
    }

    public void setInsertTime(Date insertTime)
    {
        this.insertTime = insertTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("phone", getPhone())
                .append("createTime", getCreateTime())
                .append("greenId", getGreenId())
                .append("vehPlate", getVehPlate())
                .append("vehClass", getVehClass())
                .append("vehColor", getVehColor())
                .append("submitTime", getSubmitTime())
                .append("startDistrictId", getStartDistrictId())
                .append("endDistrictId", getEndDistrictId())
                .append("insertTime", getInsertTime())
                .toString();
    }
}