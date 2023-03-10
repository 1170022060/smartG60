package com.pingok.vocational.mapper.acrossRecord;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface acrossRecordManaMapper {
    @Select({"<script>" +
            "SELECT \"vehPlate\",to_char(\"enTime\", 'yyyy-mm-dd hh24:mi:ss') as \"enTime\",\"id\",\"name\",\"passId\" FROM ( " +
            "SELECT SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) as \"vehPlate\",en.EN_TIME as \"enTime\"," +
            "b.STATION_NAME as \"name\",CONCAT('3101', b.STATION_ID) as \"id\",en.PASS_ID as \"passId\" " +
            "FROM TBL_SHAR_ENPD_RES_SENDER_${year} en  " +
            "LEFT JOIN TBL_BASE_STATION_INFO b on UPPER(b.STATION_HEX) = UPPER(SUBSTR(en.EN_TOLL_LANE_HEX, 0, 8)) " +
            "WHERE 1=1 " +
            "<when test='vehPlate != null'> " +
            "and SUBSTR(en.VEHICLE_ID, 1, INSTR(en.VEHICLE_ID, '_')-1) like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and en.EN_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and en.EN_TIME &lt;= #{endTime} " +
            "</when>"+
            "UNION ALL " +
            "SELECT SUBSTR(mj.VEHICLE_PLATE, 1, INSTR(mj.VEHICLE_PLATE, '_')-1) as \"vehPlate\",mj.TRANS_TIME as \"enTime\"," +
            "g.DEVICE_NAME as \"name\",mj.GANTRY_ID as \"id\",mj.PASS_ID as \"passId\" " +
            "FROM TBL_SHAR_GTD_RES_SENDER_${year} mj " +
            "LEFT JOIN TBL_GANTRY_INFO g on g.DEVICE_ID = mj.GANTRY_ID  " +
            "WHERE mj.GANTRY_ID in ('G006031001000120010','G006031001001110010','G006031001001110020') " +
            "<when test='vehPlate != null'> " +
            "and SUBSTR(mj.VEHICLE_PLATE, 1, INSTR(mj.VEHICLE_PLATE, '_')-1) like '%' || #{vehPlate} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            " and mj.TRANS_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and mj.TRANS_TIME &lt;= #{endTime} " +
            "</when>"+
            ") a " +
            "ORDER BY a.\"enTime\" DESC " +
            "</script>"})
    List<Map> selectAcrossRecord(@Param("year")String year, @Param("vehPlate")String vehPlate, @Param("startTime")Date startTime,@Param("endTime")Date endTime);
    
    @Select({"SELECT to_char(\"time\", 'yyyy-mm-dd hh24:mi:ss') as \"time\",\"id\",\"name\" " +
            "FROM( " +
            "SELECT  " +
            "EX_TIME as \"time\"," +
            "CONCAT('3101', s.STATION_ID) as \"id\", " +
            "EX_TOLL_STATION_NAME as \"name\" " +
            "FROM TBL_SHAR_ETCTD_RES_SENDER_${year} etc " +
            "LEFT JOIN TBL_BASE_STATION_INFO s on s.STATION_NAME = etc.EX_TOLL_STATION_NAME " +
            "WHERE PASS_ID =#{passId} " +
            "UNION ALL " +
            "SELECT  " +
            "EX_TIME as \"time\", " +
            "CONCAT('3101', s.STATION_ID) as \"id\"," +
            "EX_TOLL_STATION_NAME as \"name\" " +
            "FROM TBL_SHAR_OTD_RES_SENDER_${year} cpc " +
            "LEFT JOIN TBL_BASE_STATION_INFO s on s.STATION_NAME = cpc.EX_TOLL_STATION_NAME " +
            "WHERE PASS_ID =#{passId} " +
            "UNION ALL " +
            "SELECT  " +
            "TRANS_TIME as \"time\", " +
            "mj.GANTRY_ID as \"id\", " +
            "g.DEVICE_NAME as \"name\" " +
            "FROM TBL_SHAR_GTD_RES_SENDER_${year} mj " +
            "LEFT JOIN TBL_GANTRY_INFO g on g.DEVICE_ID = mj.GANTRY_ID " +
            "WHERE PASS_ID =#{passId}) " +
            "ORDER BY \"time\" "})
    List<Map> selectRecord(@Param("year")String year,@Param("passId")String passId);
}
