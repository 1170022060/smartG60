package com.pingok.station.domain.demicAreaList.vo;

import lombok.Data;

import java.util.Date;
@Data
public class DemicVo {
    private String stationId;

    private String stationHex;

    private String stationName;

    private String regionName;

    private Date effective;
}
