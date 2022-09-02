package com.pingok.external.mapper.weather;

import com.pingok.external.domain.weather.TblWeather2;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblWeather2Mapper extends CommonRepository<TblWeather2> {
    @Select({"<script>" +
            "select * from (" +
            "select a.*,row_number() over(partition by CITY order by UPDATETIME desc) t " +
            "from TBL_WEATHER2 a) where t = 1" +
            "</script>"})
    List<Map> selectStatus();

}
