package com.pingok.station.domain.obuBlacklist.vo;

import lombok.Data;

import java.util.Date;

@Data
public class BlackObuVo {
    private String OBUId;
    private Date creationTime;
    private Date insertTime;
    private String issuerId;
    private Integer status;
    private Integer type;
}
