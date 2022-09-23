package com.pingok.charge.domain.trans.vo;

import com.pingok.charge.domain.trans.*;
import lombok.Data;

import java.util.List;

@Data
public class ExTransEnum {
    private TblExTrans tblExTrans;
    private TblExEtcPass tblExEtcPass;
    private TblExMtcPass tblExMtcPass;
    private TblExPaperPass tblExPaperPass;
    private List<TblExTransSplit> tblExTransSplit;
}
