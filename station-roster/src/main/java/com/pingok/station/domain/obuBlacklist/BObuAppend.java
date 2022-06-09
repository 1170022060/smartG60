package com.pingok.station.domain.obuBlacklist;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class BObuAppend implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long TransId;

    /**
     * 版本号
     */
    private String ApdVer;

    /**
     * 省份号
     */
    private Integer ProvId;

    /**
     * 卡号
     */
    private String CardId;

    /**
     * 用户卡状态名单生成时间
     */
    private Date CTime;

    /**
     * 部联网中心入库时间
     */
    private Date ITime;

    /**
     * 发行服务机构编号
     */
    private String IssuerId;

    /**
     * 状态类型
     */
    private Integer CStatus;

    /**
     * 类型
     */
    private Integer CType;

    public Long getTransId() {
        return TransId;
    }

    public void setTransId(Long transId) {
        TransId = transId;
    }

    public String getApdVer() {
        return ApdVer;
    }

    public void setApdVer(String apdVer) {
        ApdVer = apdVer;
    }

    public Integer getProvId() {
        return ProvId;
    }

    public void setProvId(Integer provId) {
        ProvId = provId;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public Date getCTime() {
        return CTime;
    }

    public void setCTime(Date CTime) {
        this.CTime = CTime;
    }

    public Date getITime() {
        return ITime;
    }

    public void setITime(Date ITime) {
        this.ITime = ITime;
    }

    public String getIssuerId() {
        return IssuerId;
    }

    public void setIssuerId(String issuerId) {
        IssuerId = issuerId;
    }

    public Integer getCStatus() {
        return CStatus;
    }

    public void setCStatus(Integer CStatus) {
        this.CStatus = CStatus;
    }

    public Integer getCType() {
        return CType;
    }

    public void setCType(Integer CType) {
        this.CType = CType;
    }
}
