package com.pingok.datacenter.mapper.roster.rate;

import com.pingok.datacenter.domain.roster.rate.TblRate;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TblRateMapper extends CommonRepository<TblRate> {

    @Select("select STATION_GB as \"stationGb\" from TBL_BASE_STATION_INFO where STATION_HEX like '%310108%' and STATION_HEX != '31010804' ")
    List<String> selectStationGB();
}
