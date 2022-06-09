package com.pingok.station.domain.auditList.vo;

import lombok.Data;

import java.util.Date;
@Data
public class AuditVo {
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
