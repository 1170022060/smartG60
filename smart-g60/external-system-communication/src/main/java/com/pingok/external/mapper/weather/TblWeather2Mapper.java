package com.pingok.external.mapper.weather;

import com.pingok.external.domain.weather.TblWeather2;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface TblWeather2Mapper extends CommonRepository<TblWeather2> {
    @Select({"<script>" +
            "select WDATE as \"wdate\", WEEK as \"week\"," +
            "WEATHER as \"weather\", TEMP as \"temp\",TEMPHIGH as \"temphigh\"," +
            "TEMPLOW as \"templow\",IMG as \"img\",HUMIDITY as \"humidity\"," +
            "PRESSURE as \"pressure\",WINDSPEED as \"windspeed\",WINDDIRECT as \"winddirect\"," +
            "WINDPOWER as \"windpower\", UPDATETIME as \"updatetime\"," +
            "QUALITY as \"quality\", CITY as \"city\"" +
            " from (" +
            "select a.*,row_number() over(partition by CITY order by UPDATETIME desc) t " +
            "from TBL_WEATHER2 a) where t = 1" +
            "</script>"})
    List<Map> selectStatus();

}
