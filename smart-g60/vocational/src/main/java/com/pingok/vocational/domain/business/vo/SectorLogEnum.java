package com.pingok.vocational.domain.business.vo;

import com.pingok.vocational.domain.business.TblCpcEf02Log;
import com.pingok.vocational.domain.business.TblObuEf04Log;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SectorLogEnum {
    private Map obuEf01Log;
    private Map cpu0015Log;
    private Map cpu0019Log;
    private TblObuEf04Log obuEf04Log;
    private Map cpcEf04Log;
    private TblCpcEf02Log cpcEf02Log;
}
