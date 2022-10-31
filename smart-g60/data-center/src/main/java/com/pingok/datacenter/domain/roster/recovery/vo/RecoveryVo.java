package com.pingok.datacenter.domain.roster.recovery.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RecoveryVo {
    private String vehicleId;
    private Integer type;
    private Integer status;
    private String vehicleFeatureVersion;
    private String vehicleFeatureCode;
    private String reason;
    private Integer oweFee;
    private Integer evasionCount;
    private String version;
    private Date creationTime;
}
