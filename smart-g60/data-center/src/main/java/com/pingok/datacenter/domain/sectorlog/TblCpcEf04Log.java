package com.pingok.datacenter.domain.sectorlog;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * CPC EF04日志表 TBL_CPC_EF04_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_CPC_EF04_LOG")
public class TblCpcEf04Log implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 省份ID */
    @Excel(name = "省份ID")
    private String provinceId;

    /** 本省门架个数 */
    @Excel(name = "本省门架个数")
    private Integer gantryCount;

    /** 本省累计金额 */
    @Excel(name = "本省累计金额")
    private Integer fee;

    /** 本省累计里程 */
    @Excel(name = "本省累计里程")
    private Integer mileage;

    /** 入口ETC门架编码 */
    @Excel(name = "入口ETC门架编码")
    private String enEtcGantryHex;

    /** 入口时间 */
    @Excel(name = "入口时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTime;

    /** 最新ETC门架编码 */
    @Excel(name = "最新ETC门架编码")
    private String lastEtcGantryHex;

    /** 最新ETC门架编码 */
    @Excel(name = "最新ETC门架编码")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastPassTime;

    /** 门架拟合标识 */
    @Excel(name = "门架拟合标识")
    private Integer fitFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    @Size(min = 0, max = 2, message = "省份ID长度不能超过2个字符")
    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getGantryCount() {
        return gantryCount;
    }

    public void setGantryCount(Integer gantryCount) {
        this.gantryCount = gantryCount;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    @Size(min = 0, max = 6, message = "入口ETC门架编码长度不能超过6个字符")
    public String getEnEtcGantryHex() {
        return enEtcGantryHex;
    }

    public void setEnEtcGantryHex(String enEtcGantryHex) {
        this.enEtcGantryHex = enEtcGantryHex;
    }

    public Date getEnTime() {
        return enTime;
    }

    public void setEnTime(Date enTime) {
        this.enTime = enTime;
    }

    @Size(min = 0, max = 6, message = "最新ETC门架编码长度不能超过6个字符")
    public String getLastEtcGantryHex() {
        return lastEtcGantryHex;
    }

    public void setLastEtcGantryHex(String lastEtcGantryHex) {
        this.lastEtcGantryHex = lastEtcGantryHex;
    }

    public Date getLastPassTime() {
        return lastPassTime;
    }

    public void setLastPassTime(Date lastPassTime) {
        this.lastPassTime = lastPassTime;
    }

    public Integer getFitFlag() {
        return fitFlag;
    }

    public void setFitFlag(Integer fitFlag) {
        this.fitFlag = fitFlag;
    }
}
