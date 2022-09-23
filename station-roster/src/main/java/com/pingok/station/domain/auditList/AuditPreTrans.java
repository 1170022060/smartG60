package com.pingok.station.domain.auditList;

import java.io.Serializable;

public class AuditPreTrans implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 车辆车牌号码+颜色
     */
    private String VehicleId;
    private String ObuId;
    private String CardId;
    private String PassId;
    private String IssuerId;
    private Integer OweFee;

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getObuId() {
        return ObuId;
    }

    public void setObuId(String obuId) {
        ObuId = obuId;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getPassId() {
        return PassId;
    }

    public void setPassId(String passId) {
        PassId = passId;
    }

    public String getIssuerId() {
        return IssuerId;
    }

    public void setIssuerId(String issuerId) {
        IssuerId = issuerId;
    }

    public Integer getOweFee() {
        return OweFee;
    }

    public void setOweFee(Integer oweFee) {
        OweFee = oweFee;
    }
}
