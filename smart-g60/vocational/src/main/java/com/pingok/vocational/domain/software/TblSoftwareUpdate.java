package com.pingok.vocational.domain.software;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 软件更新记录表 TBL_SOFTWARE_UPDATE
 *
 * @author ruoyi
 */
@Table(name = "TBL_SOFTWARE_UPDATE")
public class TblSoftwareUpdate {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 软件类型 */
    @Excel(name = "软件类型")
    private Integer softwareType;

    /** 软件名称 */
    @Excel(name = "软件名称")
    private String name;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** 软件地址 */
    @Excel(name = "软件地址")
    private String location;

    /** 更新状态 1：未发布 2：已发布 0：下架 */
    @Excel(name = "更新状态")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSoftwareType() {
        return softwareType;
    }

    public void setSoftwareType(Integer softwareType) {
        this.softwareType = softwareType;
    }

    @Size(min = 0, max = 100, message = "软件名称长度不能超过100个字符")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Size(min = 0, max = 50, message = "版本号长度不能超过50个字符")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Size(min = 0, max = 255, message = "软件地址长度不能超过255个字符")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Long getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }
}
