package com.pingok.vocational.domain.customer;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * @author lal
 */
@Data
public class CustomerProblemsEx {
    /** 投诉日期 */
    @Excel(name = "投诉日期",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date complaintDate;

    /** 投诉类型 ：1.投诉 2.咨询*/
    @Excel(name = "投诉类型")
    private String complaintType;

    /** 问题描述*/
    @Excel(name = "问题描述")
    private String describe;

    /** 投诉人姓名*/
    @Excel(name = "投诉人姓名")
    private String complaintName;

    /** 投诉人联系方式*/
    @Excel(name = "投诉人联系方式")
    private String contactInfo;

    /** 处理部门 */
    @Excel(name = "处理部门")
    private String handleDept;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 处理结果*/
    @Excel(name = "回复内容")
    private String result;

    /** 处理时间 */
    @Excel(name = "回复时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date replyTime;

    /** 处理用户ID */
    @Excel(name = "回复人")
    private String replyUser;

    @Excel(name = "回复状态")
    private String status;

    /** 创建时间 */
    @Excel(name = "创建时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 创建用户ID */
    @Excel(name = "创建人")
    private String createUser;

}
