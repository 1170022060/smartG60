package com.pingok.vocational.domain.field;

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
 * 场地信息表 TBL_FIELD_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_FIELD_INFO")
public class TblFieldInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 场地名称 */
    @Excel(name = "场地名称")
    private String fieldName;

    /** 场地编码 */
    @Excel(name = "场地编码")
    private String fieldNum;

    /** 类型 ：1.停车场 2.仓库 3.机房*/
    @Excel(name = "ID",readConverterExp = "1=停车场,2=仓库,3=机房")
    private Integer type;

    /** 所属路段*/
    @Excel(name = "所属路段")
    private String roadBelong;

    /** 所属站*/
    @Excel(name = "所属站")
    private String stationBelong;

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

    @Size(min = 0, max = 30, message = "场地名称长度不能超过30个字符")
    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    @Size(min = 0, max = 20, message = "场地编码长度不能超过20个字符")
    public String getFieldNum()
    {
        return fieldNum;
    }

    public void setFieldNum(String fieldNum)
    {
        this.fieldNum = fieldNum;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    @Size(min = 0, max = 2, message = "所属路段长度不能超过2个字符")
    public String getRoadBelong()
    {
        return roadBelong;
    }

    public void setRoadBelong(String roadBelong)
    {
        this.roadBelong = roadBelong;
    }

    @Size(min = 0, max = 4, message = "所属站长度不能超过4个字符")
    public String getStationBelong()
    {
        return stationBelong;
    }

    public void setStationBelong(String stationBelong)
    {
        this.stationBelong = stationBelong;
    }

    @Size(min = 0, max = 255, message = "备注长度不能超过255个字符")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
                .append("fieldName", getFieldName())
                .append("fieldNum", getFieldNum())
                .append("type", getType())
                .append("roadBelong", getRoadBelong())
                .append("stationBelong", getStationBelong())
                .append("remark", getRemark())
                .append("status", getStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
