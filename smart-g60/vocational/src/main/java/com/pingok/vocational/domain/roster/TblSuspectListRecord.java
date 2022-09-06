package com.pingok.vocational.domain.roster;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 疑似违法车辆名单(TBL_SUSPECT_LIST_RECORD)
 * 
 * @author xia
 * @version 1.0.0 2022-08-01
 */
public class TblSuspectListRecord implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 8067746662777561121L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** 主键 */
    @Id
    private Long id;

    /** 车牌 */
    private String vehicleId;

    /** 拦截原因 */
    private String reason;

    /** 生效时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    /** 版本号 */
    private String version;

    /* This code was generated by TableGo tools, mark 1 end. */

    /* This code was generated by TableGo tools, mark 2 begin. */

    /**
     * 获取主键
     * 
     * @return 主键
     */
    public Long getId() {
        return this.id;
    }

    /**
     * 设置主键
     * 
     * @param id
     *          主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取车牌
     * 
     * @return 车牌
     */
    public String getVehicleId() {
        return this.vehicleId;
    }

    /**
     * 设置车牌
     * 
     * @param vehicleId
     *          车牌
     */
    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    /**
     * 获取拦截原因
     * 
     * @return 拦截原因
     */
    public String getReason() {
        return this.reason;
    }

    /**
     * 设置拦截原因
     * 
     * @param reason
     *          拦截原因
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * 获取生效时间
     * 
     * @return 生效时间
     */
    public Date getStartTime() {
        return this.startTime;
    }

    /**
     * 设置生效时间
     * 
     * @param startTime
     *          生效时间
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束时间
     * 
     * @return 结束时间
     */
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * 设置结束时间
     * 
     * @param endTime
     *          结束时间
     */
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取版本号
     * 
     * @return 版本号
     */
    public String getVersion() {
        return this.version;
    }

    /**
     * 设置版本号
     * 
     * @param version
     *          版本号
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /* This code was generated by TableGo tools, mark 2 end. */
}