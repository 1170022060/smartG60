package com.pingok.monitor.domain.deviceInfo;


import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * VIEW_MONITOR_DEVICE_INFO
 * @author 
 */
@Table(name = "VIEW_MONITOR_DEVICE_INFO")
public class ViewMonitorDeviceInfo implements Serializable {

    @Id
    private Long id;

    private Integer deviceCategory;

    private String deviceId;

    private String deviceName;

    private String pileNo;

    private Short direction;

    private String gps;

    private Date createTime;

    private Date updateTime;

    private String categoryName;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDeviceCategory() {
        return deviceCategory;
    }

    public void setDeviceCategory(Integer deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getPileNo() {
        return pileNo;
    }

    public void setPileNo(String pileNo) {
        this.pileNo = pileNo;
    }

    public Short getDirection() {
        return direction;
    }

    public void setDirection(Short direction) {
        this.direction = direction;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
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

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}