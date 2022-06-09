package com.pingok.station.domain.auditList;

import javax.persistence.Id;
import java.io.Serializable;

public class AuditData implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @Id
    private Long TransId;

    /**
     * 版本号
     */
    private String ApdVer;

    /**
     * 车辆车牌号码+颜色
     */
    private String VehicleId;

    /**
     * 进入黑名单原因
     */
    private String Reason;

    /**
     * 欠费金额
     */
    private Integer OweFee;

    /**
     * 欠费行为次数
     */
    private Integer EvasionCount;

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

    public String getVehicleId() {
        return VehicleId;
    }

    public void setVehicleId(String vehicleId) {
        VehicleId = vehicleId;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Integer getOweFee() {
        return OweFee;
    }

    public void setOweFee(Integer oweFee) {
        OweFee = oweFee;
    }

    public Integer getEvasionCount() {
        return EvasionCount;
    }

    public void setEvasionCount(Integer evasionCount) {
        EvasionCount = evasionCount;
    }
}
