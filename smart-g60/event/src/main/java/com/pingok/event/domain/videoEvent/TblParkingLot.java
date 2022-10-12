package com.pingok.event.domain.videoEvent;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 车位信息表(TBL_PARKING_LOT)
 * 
 * @author qiumin
 * @version 1.0.0 2022-04-08
 */
@Table(name = "TBL_PARKING_LOT")
public class TblParkingLot implements Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 6058064535609927228L;

    /* This code was generated by TableGo tools, mark 1 begin. */

    /** id */
    @Id
    private Long id;

    /** 场地id */
    private Long fieldId;

    /** 区域名称 */
    private String regionName;

    /** 区域编码 */
    private String regionNum;

    /** 总车位数 */
    private Integer total;

    /** 剩余车位数 */
    private Integer surplus;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 创建用户ID */
    private Long createUserId;

    /** 更新用户ID */
    private Long updateUserId;

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
     * 获取场地id
     * 
     * @return 场地id
     */
    public Long getFieldId() {
        return this.fieldId;
    }

    /**
     * 设置场地id
     * 
     * @param fieldId
     *          场地id
     */
    public void setFieldId(Long fieldId) {
        this.fieldId = fieldId;
    }

    /**
     * 获取区域名称
     * 
     * @return 区域名称
     */
    public String getRegionName() {
        return this.regionName;
    }

    /**
     * 设置区域名称
     * 
     * @param regionName
     *          区域名称
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 获取区域编码
     * 
     * @return 区域编码
     */
    public String getRegionNum() {
        return this.regionNum;
    }

    /**
     * 设置区域编码
     * 
     * @param regionNum
     *          区域编码
     */
    public void setRegionNum(String regionNum) {
        this.regionNum = regionNum;
    }

    /**
     * 获取总车位数
     * 
     * @return 总车位数
     */
    public Integer getTotal() {
        return this.total;
    }

    /**
     * 设置总车位数
     * 
     * @param total
     *          总车位数
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 获取剩余车位数
     * 
     * @return 剩余车位数
     */
    public Integer getSurplus() {
        return this.surplus;
    }

    /**
     * 设置剩余车位数
     * 
     * @param surplus
     *          剩余车位数
     */
    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
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

    /* This code was generated by TableGo tools, mark 2 end. */
}