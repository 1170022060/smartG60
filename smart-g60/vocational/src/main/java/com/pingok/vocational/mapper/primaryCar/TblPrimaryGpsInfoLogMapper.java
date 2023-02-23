package com.pingok.vocational.mapper.primaryCar;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TblPrimaryGpsInfoLogMapper {
    @Select({"<script>" +
            "select " +
            "LON as \"lon\"," +
            "LAT as \"lat\" " +
            "from TBL_PRIMARY_GPS_INFO_LOG " +
            "where 1=1 " +
            "<when test='vehPlate != null'>" +
            "and LICENSE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "</script>"})
    List<Map> getVehGpsList(@Param("vehPlate") String vehPlate);
}
