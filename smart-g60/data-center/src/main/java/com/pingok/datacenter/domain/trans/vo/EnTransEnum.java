package com.pingok.datacenter.domain.trans.vo;

import com.pingok.datacenter.domain.trans.TblEnMtcPass;
import com.pingok.datacenter.domain.trans.TblEnTrans;
import com.pingok.datacenter.domain.trans.TblEnEtcPass;
import lombok.Data;

@Data
public class EnTransEnum {
    private TblEnTrans tblEnTrans;
    private TblEnEtcPass tblEnEtcPass;
    private TblEnMtcPass tblEnMtcPass;
}
