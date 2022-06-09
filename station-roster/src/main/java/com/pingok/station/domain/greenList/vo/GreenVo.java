package com.pingok.station.domain.greenList.vo;
import lombok.Data;

import java.util.Date;

@Data
public class GreenVo {
    private String id;
    private Integer passStateId;
    private String vehicleSign;
    private String vehicleId;
    private String endTransportTime;
    private Date appointmentTime;
    private String endStationId;
    private String startDistrictId;
    private String endDistrictId;
}
