package com.pingok.datacenter.domain.trans.vo;

import lombok.Data;

@Data
public class ExTransInfo {

    public ExInfoVo insertExTrans;
    public int insertExEtcPass;
    public int insertExMtcPass;
    public int insertExPaperPass;
    public int insertExTransSplit;
    public int insertExTransSummary;
}
