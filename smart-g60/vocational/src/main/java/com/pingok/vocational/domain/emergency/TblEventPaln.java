package com.pingok.vocational.domain.emergency;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * 应急预案表(TBL_EVENT_PALN)
 * 
 * @author qiumin
 * @version 1.0.0 2022-04-02
 */
@Table(name = "TBL_EVENT_PALN")
public class TblEventPaln implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -8667573461996926767L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 预案标题 */
    private String planTitle;

    /** 事件类型 */
    private String eventType;

    /** 预案功能 */
    private String planFunction;

    /** 使用状态：1.启用 0.停用 */
    private Integer status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

    @Transient
    private JSONArray planFunctionList;
    @Transient
    private JSONArray eventTypeList;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取id
     * 
     * @return id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取预案标题
     * 
     * @return 预案标题
     */
    public String getPlanTitle() {
        return this.planTitle;
    }

    /**
     * 设置预案标题
     * 
     * @param planTitle
     *          预案标题
     */
    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    /**
     * 获取事件类型
     * 
     * @return 事件类型
     */
    public String getEventType() {
        return this.eventType;
    }

    /**
     * 设置事件类型
     * 
     * @param eventType
     *          事件类型
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * 获取预案功能
     * 
     * @return 预案功能
     */
    public String getPlanFunction() {
        return this.planFunction;
    }

    /**
     * 设置预案功能
     * 
     * @param planFunction
     *          预案功能
     */
    public void setPlanFunction(String planFunction) {
        this.planFunction = planFunction;
    }

    /**
     * 获取使用状态：1.启用 0.停用
     * 
     * @return 使用状态
     */
    public Integer getStatus() {
        return this.status;
    }

    /**
     * 设置使用状态：1.启用 0.停用
     * 
     * @param status
     *          使用状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取创建时间
     * 
     * @return 创建时间
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime
     *          创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 
     * @return 更新时间
     */
    public Date getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime
     *          更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建用户ID
     * 
     * @return 创建用户ID
     */
    public Long getCreateUserId() {
        return this.createUserId;
    }

    /**
     * 设置创建用户ID
     * 
     * @param createUserId
     *          创建用户ID
     */
    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    /**
     * 获取更新用户ID
     * 
     * @return 更新用户ID
     */
    public Long getUpdateUserId() {
        return this.updateUserId;
    }

    /**
     * 设置更新用户ID
     * 
     * @param updateUserId
     *          更新用户ID
     */
    public void setUpdateUserId(Long updateUserId) {
        this.updateUserId = updateUserId;
    }

    public JSONArray getPlanFunctionList() {
        return planFunctionList;
    }

    public void setPlanFunctionList(JSONArray planFunctionList) {
        this.planFunctionList = planFunctionList;
    }

    public JSONArray getEventTypeList() {
        return eventTypeList;
    }

    public void setEventTypeList(JSONArray eventTypeList) {
        this.eventTypeList = eventTypeList;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}