package com.pingok.vocational.domain.baseinfo.vo;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class StationInfo {

    private String stationId;

    private String stationName;

    private List<Map> laneInfo;
}
