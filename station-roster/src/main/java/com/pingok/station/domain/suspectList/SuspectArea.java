package com.pingok.station.domain.suspectList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class SuspectArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long RecordId;

    /**
     * 车牌
     */
    private String VehicleId;

    /**
     * 原因
     */
    private String Reason;

    /**
     * 结束时间
     */
    private Date EndTime;
    /**
     * 生效时间
     */
    private Date StartTime;

    /**
     * 版本号
     */
    private String Version;

    public Long getRecordId() {
        return RecordId;
    }

    public void setRecordId(Long recordId) {
        RecordId = recordId;
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

    public Date getEndTime() {
        return EndTime;
    }

    public void setEndTime(Date endTime) {
        EndTime = endTime;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }
}
