package com.pingok.datacenter.domain.provincialCenters.vo;

import com.pingok.datacenter.domain.provincialCenters.*;
import lombok.Data;

import java.util.List;

@Data
public class ProvincialCentersVo {
    private String year;
    private String[] Ids;
    private List<TblSharEnpdResSender> tblSharEnpdResSenders;
    private List<TblSharEtctdResSender> tblSharEtctdResSenders;
    private List<TblSharGtdResSender> tblSharGtdResSenders;
    private List<TblSharGvidResSender> tblSharGvidResSenders;
    private List<TblSharOtdResSender> tblSharOtdResSenders;
    private List<TblSharSvidResSender> tblSharSvidResSenders;
}
