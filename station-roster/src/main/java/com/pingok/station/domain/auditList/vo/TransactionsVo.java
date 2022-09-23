package com.pingok.station.domain.auditList.vo;

import lombok.Data;

@Data
public class TransactionsVo {
    private String obuId;
    private String cardId;
    private String passId;
    private String issuerId;
    private Integer oweFee;
}
