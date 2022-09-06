package com.pingok.datacenter.domain.lprtrans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 车牌识别汇总表 TBL_LPR_SUMMARY
 *
 * @author ruoyi
 */
@Table(name = "TBL_LPR_SUMMARY")
public class TblLprSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;
    /**
     * 入口识别时间
     */
    @Excel(name = "入口识别时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTransTime;

    /**
     * 入口车道流水号
     */
    @Excel(name = "入口车道流水号")
    private Integer enTransNumber;

    /**
     * 入口车道Node国标
     */
    @Excel(name = "入口车道Node国标")
    private String enLaneGb;

    /**
     * 入口车牌
     */
    @Excel(name = "入口车牌")
    private String enVehPlate;

    /**
     * 入口车牌颜色
     */
    @Excel(name = "入口车牌颜色")
    private Integer enVehColor;

    /**
     * 出口识别时间
     */
    @Excel(name = "出口识别时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exTransTime;

    /**
     * 出口车道流水号
     */
    @Excel(name = "出口车道流水号")
    private Integer exTransNumber;

    /**
     * 出口车道Node国标
     */
    @Excel(name = "出口车道Node国标")
    private String exLaneGb;

    /**
     * 出口车牌
     */
    @Excel(name = "出口车牌")
    private String exVehPlate;

    /**
     * 出口车牌颜色
     */
    @Excel(name = "出口车牌颜色")
    private Integer exVehColor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEnTransTime() {
        return enTransTime;
    }

    public void setEnTransTime(Date enTransTime) {
        this.enTransTime = enTransTime;
    }

    public Integer getEnTransNumber() {
        return enTransNumber;
    }

    public void setEnTransNumber(Integer enTransNumber) {
        this.enTransNumber = enTransNumber;
    }

    public String getEnLaneGb() {
        return enLaneGb;
    }

    public void setEnLaneGb(String enLaneGb) {
        this.enLaneGb = enLaneGb;
    }

    public String getEnVehPlate() {
        return enVehPlate;
    }

    public void setEnVehPlate(String enVehPlate) {
        this.enVehPlate = enVehPlate;
    }

    public Integer getEnVehColor() {
        return enVehColor;
    }

    public void setEnVehColor(Integer enVehColor) {
        this.enVehColor = enVehColor;
    }

    public Date getExTransTime() {
        return exTransTime;
    }

    public void setExTransTime(Date exTransTime) {
        this.exTransTime = exTransTime;
    }

    public Integer getExTransNumber() {
        return exTransNumber;
    }

    public void setExTransNumber(Integer exTransNumber) {
        this.exTransNumber = exTransNumber;
    }

    public String getExLaneGb() {
        return exLaneGb;
    }

    public void setExLaneGb(String exLaneGb) {
        this.exLaneGb = exLaneGb;
    }

    public String getExVehPlate() {
        return exVehPlate;
    }

    public void setExVehPlate(String exVehPlate) {
        this.exVehPlate = exVehPlate;
    }

    public Integer getExVehColor() {
        return exVehColor;
    }

    public void setExVehColor(Integer exVehColor) {
        this.exVehColor = exVehColor;
    }
}
