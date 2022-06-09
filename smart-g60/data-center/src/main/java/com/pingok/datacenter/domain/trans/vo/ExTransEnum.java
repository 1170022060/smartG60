package com.pingok.datacenter.domain.trans.vo;

import com.pingok.datacenter.domain.trans.*;
import lombok.Data;

import java.util.List;

@Data
public class ExTransEnum {
    private TblExTrans tblExTrans;
    private TblExTransPass tblExTransPass;
    private List<TblExTransSplit> tblExTransSplit;
}
