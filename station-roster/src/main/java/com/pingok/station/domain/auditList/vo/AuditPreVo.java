package com.pingok.station.domain.auditList.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuditPreVo {
    private String vehicleId;
    private String reason;
    private Integer oweFeeAll;
    private Integer evasionCount;
    private String version;
    private Date creationTime;
    List<TransactionsVo> transactions;
}
