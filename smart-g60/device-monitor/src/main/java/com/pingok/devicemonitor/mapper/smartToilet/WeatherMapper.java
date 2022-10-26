package com.pingok.devicemonitor.mapper.smartToilet;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface WeatherMapper {
    @Select("SELECT " +
            "WEATHER || '，气温：' || TEMP || '℃（最高：' || TEMPHIGH || '℃|最低：' || TEMPLOW || '℃），' || WINDDIRECT || WINDPOWER || '，空气质量：' || QUALITY as \"weather\" " +
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