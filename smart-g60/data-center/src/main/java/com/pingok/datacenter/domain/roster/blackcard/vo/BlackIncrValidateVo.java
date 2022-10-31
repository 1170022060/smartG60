package com.pingok.datacenter.domain.roster.blackcard.vo;

import lombok.Data;

import java.util.List;

@Data
public class BlackIncrValidateVo {
    private List<ProvInfo> data;
    private Integer type;
    private String version;
}
