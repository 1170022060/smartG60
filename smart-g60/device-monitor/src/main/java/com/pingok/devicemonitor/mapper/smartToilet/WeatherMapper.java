package com.pingok.devicemonitor.mapper.smartToilet;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WeatherMapper {
    @Select("SELECT " +
            "WEATHER || ' 温度 '  || TEMPHIGH || '-' || TEMPLOW || '℃ 湿度 ' || HUMIDITY || '%'  as \"weather\" " +
            "FROM " +
            "TBL_WEATHER2  " +
            "WHERE " +
            "CITY = '上海'  " +
            "AND WDATE = #{date}  " +
            "AND ROWNUM = 1 " +
            "ORDER BY " +
            "UPDATETIME DESC ")
    Object weather(@Param("date") String date);
}