package com.pingok.datacenter.domain.sectorlog;

import com.ruoyi.common.core.annotation.Excel;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * CPU 0015日志表 TBL_CPU_0015_LOG
 *
 * @author ruoyi
 */
@Table(name = "TBL_CPU_0015_LOG")
public class TblCpu0015Log implements Serializable {

    private static final long serialVersionUID = 1L;
    /** 主键ID */
    @Excel(name = "ID")
    @Id
    private Long id;

    /** 对应扇区日志表ID */
    @Excel(name = "对应扇区日志表ID")
    private Long logId;

    /** 发卡方标识 */
    @Excel(name = "发卡方标识")
    private String issuerIdentity;

    /** 卡片类型 */
    @Excel(name = "卡片类型")
    private Integer cardType;

    /** 卡片版本号 */
    @Excel(name = "卡片版本号")
    private Integer cardVersion;

    /** 卡片网络编号 */
    @Excel(name = "卡片网络编号")
    private String cardNet;

    /** CPU卡片内部编号 */
    @Excel(name = "CPU卡片内部编号")
    private String cardId;

    /** 启用时间 */
    @Excel(name = "启用时间")
    private String enableTime;

    /** 到期时间 */
    @Excel(name = "到期时间")
    private String expirationTime;

    /** 车牌 */
    @Excel(name = "车牌")
    private String vehPlate;

    /** 用户类型 */
    @Excel(name = "用户类型")
    private Integer userType;

    /** 车牌颜色 */
    @Excel(name = "车牌颜色")
    private Integer vehColor;

    /** 车型 */
    @Excel(name = "车型")
    private Integer vehClass;

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

    @Size(min = 0, max = 16, message = "发卡方标识长度不能超过16个字符")
    public String getIssuerIdentity() {
        return issuerIdentity;
    }

    public void setIssuerIdentity(String issuerIdentity) {
        this.issuerIdentity = issuerIdentity;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public Integer getCardVersion() {
        return cardVersion;
    }

    public void setCardVersion(Integer cardVersion) {
        this.cardVersion = cardVersion;
    }

    @Size(min = 0, max = 4, message = "卡片网络编号长度不能超过4个字符")
    public String getCardNet() {
        return cardNet;
    }

    public void setCardNet(String cardNet) {
        this.cardNet = cardNet;
    }

    @Size(min = 0, max = 20, message = "CPU卡片内部编号长度不能超过20个字符")
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    @Size(min = 0, max = 8, message = "启用时间长度不能超过8个字符")
    public String getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(String enableTime) {
        this.enableTime = enableTime;
    }

    @Size(min = 0, max = 8, message = "到期时间长度不能超过8个字符")
    public String getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Size(min = 0, max = 20, message = "车牌长度不能超过20个字符")
    public String getVehPlate() {
        return vehPlate;
    }

    public void setVehPlate(String vehPlate) {
        this.vehPlate = vehPlate;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
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
}
