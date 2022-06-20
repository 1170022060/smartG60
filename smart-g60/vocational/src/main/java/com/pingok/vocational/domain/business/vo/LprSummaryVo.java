package com.pingok.vocational.domain.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 车道牌识(导出格式)
 *
 * @author ruoyi
 */
public class LprSummaryVo implements Serializable {

    private static final long serialVersionUID = 1L;
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
     * 入口车道名
     */
    @Excel(name = "入口车道名")
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
    private String enVehColor;

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
     * 出口车道名
     */
    @Excel(name = "出口车道名")
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
    private String exVehColor;

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

    public String getEnVehColor() {
        return enVehColor;
    }

    public void setEnVehColor(String enVehColor) {
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

    public String getExVehColor() {
        return exVehColor;
    }

    public void setExVehColor(String exVehColor) {
        this.exVehColor = exVehColor;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("enTransTime", getEnTransTime())
                .append("enTransNumber", getEnTransNumber())
                .append("enLaneGb", getEnLaneGb())
                .append("enVehPlate", getEnVehPlate())
                .append("enVehColor", getEnVehColor())
                .append("exTransTime", getExTransTime())
                .append("exTransNumber", getExTransNumber())
                .append("exLaneGb", getExLaneGb())
                .append("exVehPlate", getExVehPlate())
                .append("exVehColor", getExVehColor())
                .toString();
    }
}
