package com.pingok.vocational.domain.device;

import com.ruoyi.common.core.annotation.Excel;

import javax.validation.constraints.Size;
import java.util.Date;

public class DeviceGetCategory {


    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    private Long id;

    /** 类目名称*/
    @Excel(name = "类目名称")
    private String categoryName;

    /** 父级类目*/
    @Excel(name = "父级类目")
    private String parentCategoryName;

    /** 使用状态 ：1.启用 0.停用*/
    @Excel(name = "使用状态",readConverterExp = "1=启用,2=停用")
    private Byte status;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private Date createTime;

    /** 更新时间 */
    @Excel(name = "更新时间")
    private Date updateTime;

    /** 创建用户ID */
    @Excel(name = "创建用户工号")
    private String createUserId;

    /** 更新用户ID */
    @Excel(name = "更新用户ID")
    private String updateUserId;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCategoryName()
    {
        return categoryName;
    }

    public void setCategoryName(String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getParentCategoryName()
    {
        return parentCategoryName;
    }

    public void setParentCategoryName(String parentCategoryName)
    {
        this.parentCategoryName = parentCategoryName;
    }

    public Byte getStatus()
    {
        return status;
    }

    public void setStatus(Byte type)
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

    public String getCreateUserId()
    {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) { this.createUserId = createUserId; }

    public String getUpdateUserId()
    {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) { this.updateUserId = updateUserId; }
}
