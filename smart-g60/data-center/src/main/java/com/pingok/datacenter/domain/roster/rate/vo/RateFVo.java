package com.pingok.datacenter.domain.roster.rate.vo;

import lombok.Data;

import java.util.List;

@Data
public class RateFVo {
    private String enProv;
    private String enID;
    private String exProv;
    private String exID;
    private Integer vType;
    private Integer m;
    List<RateFSplitVo> splitProvince;
}
