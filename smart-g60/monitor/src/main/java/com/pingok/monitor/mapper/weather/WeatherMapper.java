package com.pingok.monitor.mapper.weather;

import com.pingok.monitor.domain.weather.TblWeather;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WeatherMapper extends CommonRepository<TblWeather> {
    @Select("SELECT    " +
            "WEATHER as \"weather\"," +
            "TEMP as \"airTemp\"," +
            "TEMPHIGH as \"tempHigh\"," +
            "TEMPLOW as \"tempLow\"," +
            "WINDDIRECT as \"windDirect\"," +
            "WINDPOWER as \"windPower\"," +
            "WINDSPEED as \"windSpeed\"," +
            "QUALITY as \"airQuality\"  " +
            "FROM  " +
            "TBL_WEATHER2   " +
            "WHERE  " +
            "CITY = '上海'   " +
            "AND ROWNUM = 1  " +
            "ORDER BY  " +
            "UPDATETIME DESC ")
    List<TblWeather> weather();

}