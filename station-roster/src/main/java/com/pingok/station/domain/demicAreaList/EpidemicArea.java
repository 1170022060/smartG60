package com.pingok.station.domain.demicAreaList;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

public class EpidemicArea implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private Long RecordId;

    private String StationID;

    private String StationHEX;

    private String StationName;

    private String RegionName;

    private Date StartTime;

    public Long getRecordId() {
        return RecordId;
    }

    public void setRecordId(Long recordId) {
        RecordId = recordId;
    }

    public String getStationID() {
        return StationID;
    }

    public void setStationID(String stationID) {
        StationID = stationID;
    }

    public String getStationHEX() {
        return StationHEX;
    }

    public void setStationHEX(String stationHEX) {
        StationHEX = stationHEX;
    }

    public String getStationName() {
        return StationName;
    }

    public void setStationName(String stationName) {
        StationName = stationName;
    }

    public String getRegionName() {
        return RegionName;
    }

    public void setRegionName(String regionName) {
        RegionName = regionName;
    }

    public Date getStartTime() {
        return StartTime;
    }

    public void setStartTime(Date startTime) {
        StartTime = startTime;
    }
}
