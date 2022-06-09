package com.pingok.vocational.domain.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 项目信息表 TBL_PROJECT_INFO
 *
 * @author ruoyi
 */
@Table(name = "TBL_PROJECT_INFO")
public class TblProjectInfo {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 项目编码 */
    @Excel(name = "项目编码")
    private String projectNum;

    /** 项目名称 */
    @Excel(name = "项目名称")
    private String projectName;

    /** 项目时间 */
    @Excel(name = "项目时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date projectTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectNum() {
        return projectNum;
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getProjectTime() {
        return projectTime;
    }

    public void setProjectTime(Date projectTime) {
        this.projectTime = projectTime;
    }
}
