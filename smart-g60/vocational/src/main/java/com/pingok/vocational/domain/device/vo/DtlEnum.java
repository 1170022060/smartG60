package com.pingok.vocational.domain.device.vo;

import com.pingok.vocational.domain.device.TblGantryInfoDtl;
import lombok.Data;

import java.util.List;

@Data
public class DtlEnum {
    private Long infoId;
    private List<TblGantryInfoDtl> dtlList;
}
