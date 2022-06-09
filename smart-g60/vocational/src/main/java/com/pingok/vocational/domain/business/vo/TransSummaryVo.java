package com.pingok.vocational.domain.business.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 收费流水(导出格式)
 *
 * @author ruoyi
 */
public class TransSummaryVo {

    private static final long serialVersionUID = 1L;

    /**
     * PassId
     */
    @Excel(name = "PassId")
    private String passId;

    /**
     * 入口GID
     */
    @Excel(name = "入口GID")
    private String enGid;

    /**
     * 入口时间
     */
    @Excel(name = "入口时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enTransTime;

    /**
     * 入口工班日
     */
    @Excel(name = "入口工班日",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date enWorkDate;

    /**
     * 入口通行方式
     */
    @Excel(name = "入口通行方式")
    private String enPassType;

    /**
     * 入口站名
     */
    @Excel(name = "入口站名")
    private String enStation;

    /**
     * 入口车道名
     */
    @Excel(name = "入口车道名")
    private String enLaneHex;

    /**
     * 入口工班
     */
    @Excel(name = "入口工班")
    private String enShift;

    /**
     * 入口车型
     */
    @Excel(name = "入口车型")
    private String enVehClass;

    /**
     * 入口车种
     */
    @Excel(name = "入口车种")
    private String enVehStatus;

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
     * 入口CPC/ETC卡ID
     */
    @Excel(name = "入口CPC/ETC卡ID")
    private String enCardId;

    /**
     * 出口GID
     */
    @Excel(name = "出口GID")
    private String exGid;

    /**
     * 出口时间
     */
    @Excel(name = "出口时间",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exTransTime;

    /**
     * 出口工班日
     */
    @Excel(name = "出口工班日",dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date exWorkDate;

    /**
     * 出口通行方式
     */
    @Excel(name = "出口通行方式")
    private String exPassType;

    /**
     * 出口站名
     */
    @Excel(name = "出口站名")
    private String exStation;

    /**
     * 出口车道名
     */
    @Excel(name = "出口车道名")
    private String exLaneHex;

    /**
     * 出口工班
     */
    @Excel(name = "出口工班")
    private String exShift;

    /**
     * 出口车型
     */
    @Excel(name = "出口车型")
    private String exVehClass;

    /**
     * 出口车种
     */
    @Excel(name = "出口车种")
    private String exVehStatus;

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

    /**
     * 出口CPC/ETC卡ID
     */
    @Excel(name = "出口CPC/ETC卡ID")
    private String exCardId;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    private String payWay;

    /**
     * 应收金额
     */
    @Excel(name = "应收金额")
    private Integer tollFee;

    /**
     * 优惠金额
     */
    @Excel(name = "优惠金额")
    private Integer discountFee;

    /**
     * 实收金额
     */
    @Excel(name = "实收金额")
    private Integer amount;

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getEnGid() {
        return enGid;
    }

    public void setEnGid(String enGid) {
        this.enGid = enGid;
    }

    public Date getEnTransTime() {
        return enTransTime;
    }

    public void setEnTransTime(Date enTransTime) {
        this.enTransTime = enTransTime;
    }

    public Date getEnWorkDate() {
        return enWorkDate;
    }

    public void setEnWorkDate(Date enWorkDate) {
        this.enWorkDate = enWorkDate;
    }

    public String getEnPassType() {
        return enPassType;
    }

    public void setEnPassType(String enPassType) {
        this.enPassType = enPassType;
    }

    public String getEnStation() {
        return enStation;
    }

    public void setEnStation(String enStation) {
        this.enStation = enStation;
    }

    public String getEnLaneHex() {
        return enLaneHex;
    }

    public void setEnLaneHex(String enLaneHex) {
        this.enLaneHex = enLaneHex;
    }

    public String getEnShift() {
        return enShift;
    }

    public void setEnShift(String enShift) {
        this.enShift = enShift;
    }

    public String getEnVehClass() {
        return enVehClass;
    }

    public void setEnVehClass(String enVehClass) {
        this.enVehClass = enVehClass;
    }

    public String getEnVehStatus() {
        return enVehStatus;
    }

    public void setEnVehStatus(String enVehStatus) {
        this.enVehStatus = enVehStatus;
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

    public String getEnCardId() {
        return enCardId;
    }

    public void setEnCardId(String enCardId) {
        this.enCardId = enCardId;
    }

    public String getExGid() {
        return exGid;
    }

    public void setExGid(String exGid) {
        this.exGid = exGid;
    }

    public Date getExTransTime() {
        return exTransTime;
    }

    public void setExTransTime(Date exTransTime) {
        this.exTransTime = exTransTime;
    }

    public Date getExWorkDate() {
        return exWorkDate;
    }

    public void setExWorkDate(Date exWorkDate) {
        this.exWorkDate = exWorkDate;
    }

    public String getExPassType() {
        return exPassType;
    }

    public void setExPassType(String exPassType) {
        this.exPassType = exPassType;
    }

    public String getExStation() {
        return exStation;
    }

    public void setExStation(String exStation) {
        this.exStation = exStation;
    }

    public String getExLaneHex() {
        return exLaneHex;
    }

    public void setExLaneHex(String exLaneHex) {
        this.exLaneHex = exLaneHex;
    }

    public String getExShift() {
        return exShift;
    }

    public void setExShift(String exShift) {
        this.exShift = exShift;
    }

    public String getExVehClass() {
        return exVehClass;
    }

    public void setExVehClass(String exVehClass) {
        this.exVehClass = exVehClass;
    }

    public String getExVehStatus() {
        return exVehStatus;
    }

    public void setExVehStatus(String exVehStatus) {
        this.exVehStatus = exVehStatus;
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

    public String getExCardId() {
        return exCardId;
    }

    public void setExCardId(String exCardId) {
        this.exCardId = exCardId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public Integer getTollFee() {
        return tollFee;
    }

    public void setTollFee(Integer tollFee) {
        this.tollFee = tollFee;
    }

    public Integer getDiscountFee() {
        return discountFee;
    }

    public void setDiscountFee(Integer discountFee) {
        this.discountFee = discountFee;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("passId", getPassId())
                .append("enGid", getEnGid())
                .append("enTransTime", getEnTransTime())
                .append("enWorkDate", getEnWorkDate())
                .append("enPassType", getEnPassType())
                .append("enStation", getEnStation())
                .append("enLaneHex", getEnLaneHex())
                .append("enShift", getEnShift())
                .append("enVehClass", getEnVehClass())
                .append("enVehStatus", getEnVehStatus())
                .append("enVehPlate", getEnVehPlate())
                .append("enVehColor", getEnVehColor())
                .append("enCardId", getEnCardId())
                .append("exGid", getExGid())
                .append("exTransTime", getExTransTime())
                .append("exWorkDate", getExWorkDate())
                .append("exPassType", getExPassType())
                .append("exStation", getExStation())
                .append("exLaneHex", getExLaneHex())
                .append("exShift", getExShift())
                .append("exVehClass", getExVehClass())
                .append("exVehStatus", getExVehStatus())
                .append("exVehPlate", getExVehPlate())
                .append("exVehColor", getExVehColor())
                .append("exCardId", getExCardId())
                .append("payWay", getPayWay())
                .append("tollFee", getTollFee())
                .append("discountFee", getDiscountFee())
                .append("amount", getAmount())
                .toString();
    }
}
