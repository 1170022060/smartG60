package com.pingok.station.domain.cardBlacklist.vo;

import lombok.Data;

import java.util.Date;
@Data
public class BlackVo {
    private String cardId;
    private Date creationTime;
    private Date insertTime;
    private String issuerId;
    private Integer status;
    private Integer type;
}
