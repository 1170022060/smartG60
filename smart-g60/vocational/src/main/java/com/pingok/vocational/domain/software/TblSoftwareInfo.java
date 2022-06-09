package com.pingok.vocational.domain.software;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 软件信息表 TBL_SOFTWARE_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_SOFTWARE_INFO")
public class TblSoftwareInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 软件编码 */
    @Excel(name = "软件编码")
    private String num;

    /** 软件名称 */
    @Excel(name = "软件名称")
    private String name;

    /** 版本号 */
    @Excel(name = "版本号")
    private String version;

    /** ip */
    @Excel(name = "版本号")
    private String ip;

    /** 心跳时间 */
    @Excel(name = "心跳时间")
    private Date time;

    /** 运行状态：1-正常 0-异常 */
    @Excel(name = "运行状态")
    private Integer status;

    /** 状态描述 */
    @Excel(name = "状态描述")
    private String statusCode;

    /** 上传标志位：1-有上传业务 0-无上传业务 */
    @Excel(name = "上传标志位")
    private Integer uploadFlag;

    /** 上传状态数组 */
    @Excel(name = "上传状态数组")
    private String uploadStatus;

    /** 下载标志位：1-有下载业务 0-无下载业务 */
    @Excel(name = "下载标志位")
    private Integer downloadFlag;

    /** 下载状态数组 */
    @Excel(name = "下载状态数组")
    private String downloadStatus;

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

    @Size(min = 0, max = 100, message = "软件编码长度不能超过100个字符")
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    @Size(min = 0, max = 20, message = "IP长度不能超过20个字符")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Size(min = 0, max = 255, message = "状态描述长度不能超过255个字符")
    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getUploadFlag() {
        return uploadFlag;
    }

    public void setUploadFlag(Integer uploadFlag) {
        this.uploadFlag = uploadFlag;
    }

    @Size(min = 0, max = 1000, message = "上传状态数组长度不能超过1000个字符")
    public String getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Integer getDownloadFlag() {
        return downloadFlag;
    }

    public void setDownloadFlag(Integer downloadFlag) {
        this.downloadFlag = downloadFlag;
    }

    @Size(min = 0, max = 1000, message = "下载状态数组长度不能超过1000个字符")
    public String getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(String downloadStatus) {
        this.downloadStatus = downloadStatus;
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