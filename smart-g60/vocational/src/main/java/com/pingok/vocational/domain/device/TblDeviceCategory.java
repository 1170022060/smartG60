package com.pingok.vocational.domain.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备类目表 TBL_DEVICE_CATEGORY
 *
 * @author ruoyi
 */
@Table(name = "TBL_DEVICE_CATEGORY")
public class TblDeviceCategory implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 类目名称*/
    @Excel(name = "类目名称")
    private String categoryName;

    /** 类目编码*/
    @Excel(name = "类目编码")
    private String categoryNum;

    /** 父级类目*/
    @Excel(name = "父级类目")
    private Long parentCategory;

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

    /** 子菜单 */
    private List<TblDeviceCategory> children = new ArrayList<TblDeviceCategory>();

    /** 绑定岗位*/
    @Excel(name = "绑定岗位")
    private String categoryPost;

    /** 绑定岗位数组*/
    @Excel(name = "绑定岗位数组")
    private Long[] categoryPostStr;

    @Transient
    private List<Map> deviceInfos;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    @Size(min = 0, max = 30, message = "类目名称长度不能超过20个字符")
    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public Long getParentCategory()
    {
        return parentCategory;
    }

    public void setParentCategory(Long parentCategory)
    {
        this.parentCategory = parentCategory;
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

    public List<TblDeviceCategory> getChildren()
    {
        return children;
    }

    public void setChildren(List<TblDeviceCategory> children)
    {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id",getId())
                .append("groupName",getCategoryName())
                .append("groupLeader",getParentCategory())
                .append("status",getStatus())
                .append("createTime",getCreateTime())
                .append("updateTime",getUpdateTime())
                .append("createUserId",getCreateUserId())
                .append("updateUserId",getUpdateUserId())
                .toString();
    }

    public String getCategoryNum() {
        return categoryNum;
    }

    public void setCategoryNum(String categoryNum) {
        this.categoryNum = categoryNum;
    }

    public List<Map> getDeviceInfos() {
        return deviceInfos;
    }

    public void setDeviceInfos(List<Map> deviceInfos) {
        this.deviceInfos = deviceInfos;
    }

    public String getCategoryPost() {
        return categoryPost;
    }

    public void setCategoryPost(String categoryPost) {
        this.categoryPost = categoryPost;
    }

    public Long[] getCategoryPostStr() {
        return categoryPostStr;
    }

    public void setCategoryPostStr(Long[] categoryPostStr) {
        this.categoryPostStr = categoryPostStr;
    }
}
