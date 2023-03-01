package com.pingok.vocational.domain.event;

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
 * 事件记录表 TBL_EVENT_RECORD
 *
 * @author ruoyi
 */
@Table(name = "TBL_EVENT_RECORD")
public class TblEventRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 主键ID
     */
    @Excel(name = "ID")
    @Id
    private Long id;

    /**
     * 事件类型
     */
    @Excel(name = "事件类型")
    private String eventType;

    /**
     * 位置区间
     */
    @Excel(name = "位置区间")
    private String locationInterval;

    /**
     * 车型
     */
    @Excel(name = "车型")
    private Integer vehClass;

    /**
     * 车牌
     */
    @Excel(name = "车牌")
    private String vehPlate;

    /**
     * 车牌颜色
     */
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /**
     * 事件照片
     */
    @Excel(name = "事件照片")
    private String eventPhoto;

    /**
     * 事件发生时间
     */
    @Excel(name = "事件发生时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date eventTime;

    /**
     * 车速
     */
    @Excel(name = "车速")
    private Integer speed;

    /**
     * 车道
     */
    @Excel(name = "车道")
    private String lane;

    /**
     * 视频
     */
    @Excel(name = "视频")
    private String video;


    /**
     * 确认时间
     */
    @Excel(name = "确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date confirmTime;

    /**
     * 解除确认时间
     */
    @Excel(name = "解除确认时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date relieveTime;

    /**
     * 确认用户ID
     */
    @Excel(name = "确认用户ID")
    private Long confirmUserId;

    /**
     * 解除确认用户ID
     */
    @Excel(name = "解除确认用户ID")
    private Long relieveUserId;

    /**
     * 状态 -1-误报 0-新增 1-已确认 2-已处置
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    /**
     * 创建用户ID
     */
    private Long createUserId;

    /**
     * 更新用户ID
     */
    private Long updateUserId;

    private Long eventId;

    private String remark;

    /**
     * 上行、下行、双向
     */
    private String direction;


    /**
     * 桩号
     */
    private String pileNo;


    /**
     * 检测设备类型
     */
    private Integer deviceType;

    private Long ubiPtzShortId;

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(Integer eventLevel) {
        this.eventLevel = eventLevel;
    }

    private Integer eventLevel;

    public String getPileNo() {
        return pileNo;
    }

    public void setPileNo(String pileNo) {
        this.pileNo = pileNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Size(min = 0, max = 50, message = "事件类型不能超过50个字符")
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Size(min = 0, max = 100, message = "位置区间不能超过100个字符")
    public String getLocationInterval() {
        return locationInterval;
    }

    public void setLocationInterval(String locationInterval) {
        this.locationInterval = locationInterval;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
    public String getVehPlate() {
        return vehPlate;
    }

    public void setVehPlate(String vehPlate) {
        this.vehPlate = vehPlate;
    }

    public Integer getVehColor() {
        return vehColor;
    }

    public void setVehColor(Integer vehColor) {
        this.vehColor = vehColor;
    }

    public Integer getVehClass() {
        return vehClass;
    }

    public void setVehClass(Integer vehClass) {
        this.vehClass = vehClass;
    }

    @Size(min = 0, max = 255, message = "事件照片不能超过255个字符")
    public String getEventPhoto() {
        return eventPhoto;
    }

    public void setEventPhoto(String eventPhoto) {
        this.eventPhoto = eventPhoto;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Size(min = 0, max = 20, message = "车道不能超过20个字符")
    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    @Size(min = 0, max = 255, message = "视频不能超过20个字符")
    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Date getRelieveTime() {
        return relieveTime;
    }

    public void setRelieveTime(Date relieveTime) {
        this.relieveTime = relieveTime;
    }

    public Long getConfirmUserId() {
        return confirmUserId;
    }

    public void setConfirmUserId(Long confirmUserId) {
        this.confirmUserId = confirmUserId;
    }

    public Long getRelieveUserId() {
        return relieveUserId;
    }

    public void setRelieveUserId(Long relieveUserId) {
        this.relieveUserId = relieveUserId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("eventType", getEventType())
                .append("locationInterval", getLocationInterval())
                .append("vehClass", getVehClass())
                .append("vehPlate", getVehPlate())
                .append("vehColor", getVehColor())
                .append("eventPhoto", getEventPhoto())
                .append("eventTime", getEventTime())
                .append("speed", getSpeed())
                .append("lane", getLane())
                .append("video", getVideo())
                .append("confirmTime", getConfirmTime())
                .append("relieveTime", getRelieveTime())
                .append("confirmUserId", getConfirmUserId())
                .append("relieveUserId", getRelieveUserId())
                .toString();
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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Long getUbiPtzShortId() {
        return ubiPtzShortId;
    }

    public void setUbiPtzShortId(Long ubiPtzShortId) {
        this.ubiPtzShortId = ubiPtzShortId;
    }
}
