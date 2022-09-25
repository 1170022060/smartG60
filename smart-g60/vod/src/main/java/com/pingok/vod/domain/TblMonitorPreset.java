package com.pingok.vod.domain;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 枪云监控预设表(TBL_MONITOR_PRESET)
 * 
 * @author qiumin
 * @version 1.0.0 2022-03-25
 */
@Table(name = "TBL_MONITOR_PRESET")
public class TblMonitorPreset  implements Serializable {

    /** id */
    @Id
    private Long id;

    /** 设备id */
    private Long deviceId;

    /** 用户id */
    private Long userId;

    /** CREATE_TIME */
    private Date createTime;
    
    @Transient
    private List<Long> ids;

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
     * 获取设备id
     * 
     * @return 设备id
     */
    public Long getDeviceId() {
        return this.deviceId;
    }

    /**
     * 设置设备id
     * 
     * @param deviceId
     *          设备id
     */
    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * 获取用户id
     * 
     * @return 用户id
     */
    public Long getUserId() {
        return this.userId;
    }

    /**
     * 设置用户id
     * 
     * @param userId
     *          用户id
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取CREATE_TIME
     * 
     * @return CREATE_TIME
     */
    public Date getCreateTime() {
        return this.createTime;
    }

    /**
     * 设置CREATE_TIME
     * 
     * @param createTime
     *          CREATE_TIME
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}