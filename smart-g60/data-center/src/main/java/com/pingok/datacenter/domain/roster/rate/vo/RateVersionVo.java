package com.pingok.datacenter.domain.roster.rate.vo;

import lombok.Data;

import java.util.Date;
@Data
public class RateVersionVo {
    private String exStationId;
    private Date validTime;
    private String provinceId;
    private String version;
}
