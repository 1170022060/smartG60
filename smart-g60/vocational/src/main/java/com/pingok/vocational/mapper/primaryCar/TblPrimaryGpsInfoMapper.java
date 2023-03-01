package com.pingok.vocational.mapper.primaryCar;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TblPrimaryGpsInfoMapper {

    @Select({"<script>" +
            "SELECT  " +
            "ID as \"id\", " +
            "LICENSE as \"vehPlate\", " +
            "COLOR as \"vehColor\", " +
            "LON as \"lon\", " +
            "LAT as \"lat\", " +
            "to_char(TIME,'yyyy-mm-dd hh24:mi:ss') as \"time\", " +
            "SPEED as \"speed\", " +
            "DIRECTION as \"direction\", " +
            "ALTITUDE as \"altitude\", " +
            "CASE WHEN ACC=0 THEN '关' ELSE '开' END as \"acc\" " +
            "FROM TBL_PRIMARY_GPS_INFO " +
            "where 1=1 " +
            "<when test='vehPlate != null'>" +
            "and LICENSE like '%' || #{vehPlate} || '%' " +
            "</when> " +
            "</script>"})
    List<Map> selectPrimaryGpsInfo(@Param("vehPlate") String vehPlate);
}
