package com.pingok.devicemonitor.domain.gantry.vo;

import lombok.Data;

@Data
public class ChargeFlowModel {
    private String chargingUnitId;
    private String statisticsDate;
    private Long flow;
}
