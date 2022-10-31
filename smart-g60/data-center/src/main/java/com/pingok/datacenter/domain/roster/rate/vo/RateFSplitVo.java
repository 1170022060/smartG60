package com.pingok.datacenter.domain.roster.rate.vo;

import lombok.Data;

@Data
public class RateFSplitVo {
    private Integer index;
    private String prov;
    private Integer pFee;
    private Integer pFee95;
    private Integer pM;
}
