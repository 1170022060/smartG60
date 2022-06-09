package com.pingok.datacenter.domain.sectorlog.vo;

import lombok.Data;

@Data
public class SectorInfo {

    public Long insertSectorLog;
    public int insertCpcEf02Log;
    public int insertCpcEf04Log;
    public int insertCpu0015Log;
    public int insertCpu0019Log;
    public int insertObuEf01Log;
    public Long insertObuEf04Log;
    public int insertObuEf04LogProv;
}
