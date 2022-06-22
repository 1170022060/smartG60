package com.pingok.vocational.domain.customer;

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
 * 客户投诉与咨询记录表 TBL_CUSTOMER_PROBLEMS
 *
 * @author ruoyi
 */
@Table(name = "TBL_CUSTOMER_PROBLEMS")
public class TblCustomerProblems implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 投诉日期 */
    @Excel(name = "投诉日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date complaintDate;

    /** 投诉类型 ：1.投诉 2.咨询*/
    @Excel(name = "投诉类型",readConverterExp = "1=投诉,2=咨询")
    private Integer complaintType;

    /** 问题描述*/
    @Excel(name = "问题描述")
    private String describe;

    /** 处理结果*/
    @Excel(name = "处理结果")
    private String result;

    /** 投诉人姓名*/
    @Excel(name = "投诉人姓名")
    private String complaintName;

    /** 投诉人联系方式*/
    @Excel(name = "投诉人联系方式")
    private String contactInfo;

    /** 处理部门 */
    @Excel(name = "处理部门")
    private Long handleDept;

    /** 处理时间 */
    @Excel(name = "处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date handleTime;

    /** 创建时间 */
    @Excel(name = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 处理用户ID */
    @Excel(name = "处理用户工号")
    private Long handleUserId;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private Long createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private Long updateUserId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getComplaintDate() {
        return complaintDate;
    }

    public void setComplaintDate(Date complaintDate) {
        this.complaintDate = complaintDate;
    }

    public Integer getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(Integer complaintType) {
        this.complaintType = complaintType;
    }

    @Size(min = 0, max = 200, message = "问题描述长度不能超过200个字符")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Size(min = 0, max = 200, message = "处理结果不能超过200个字符")
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Size(min = 0, max = 20, message = "投诉人姓名不能超过20个字符")
    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    @Size(min = 0, max = 20, message = "投诉人联系方式不能超过20个字符")
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public Long getHandleDept() {
        return handleDept;
    }

    public void setHandleDept(Long handleDept) {
        this.handleDept = handleDept;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getHandleUserId() {
        return handleUserId;
    }

    public void setHandleUserId(Long handleUserId) {
        this.handleUserId = handleUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("complaintDate", getComplaintDate())
                .append("complaintType", getComplaintType())
                .append("describe", getDescribe())
                .append("result", getResult())
                .append("complaintName", getComplaintName())
                .append("contactInfo", getContactInfo())
                .append("handleTime", getHandleTime())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("handleUserId", getHandleUserId())
                .append("createUserId", getCreateUserId())
                .append("updateUserId", getUpdateUserId())
                .toString();
    }
}
