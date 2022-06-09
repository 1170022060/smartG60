package com.pingok.charge.domain.trans.vo;

import com.pingok.charge.domain.trans.TblEnEtcPass;
import com.pingok.charge.domain.trans.TblEnMtcPass;
import com.pingok.charge.domain.trans.TblEnTrans;
import lombok.Data;

@Data
public class EnTransEnum {
    private TblEnTrans tblEnTrans;
    private TblEnEtcPass tblEnEtcPass;
    private TblEnMtcPass tblEnMtcPass;
}
