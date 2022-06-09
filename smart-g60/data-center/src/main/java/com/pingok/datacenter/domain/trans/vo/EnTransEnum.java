package com.pingok.datacenter.domain.trans.vo;

import com.pingok.datacenter.domain.trans.TblEnTrans;
import com.pingok.datacenter.domain.trans.TblEnTransPass;
import lombok.Data;

@Data
public class EnTransEnum {
    private TblEnTrans tblEnTrans;
    private TblEnTransPass tblEnTransPass;
}
