package com.pingok.vocational.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface RoadFlowStatisMapper {
    @Select({"<script>" +
            "SELECT  " +
            "r.VELOCITY as \"velocity\", " +
            "r.FLOW as \"flow\", " +
            "r.CONGESTION as \"jamLevel\", " +
            "to_char( r.START_TIME, 'yyyy-mm-dd hh24:mi:ss' ) as \"startTime\", " +
            "to_char( r.END_TIME, 'yyyy-mm-dd hh24:mi:ss' ) as \"endTime\", " +
            "c.\"name\" as \"origin\", " +
            "d.\"name\" as \"destination\", " +
            "a.LENGTH as \"length\", " +
            "a.MESSAGE as \"message\" " +
            "FROM TBL_ROAD_VOLUME_VELOCITY r " +
            "LEFT JOIN TBL_ROAD_INFO a on a.ROAD_ID = r.ROAD_ID " +
            "LEFT JOIN ( " +
            "SELECT s.STATION_NAME as \"name\",s.STATION_ID as \"stationId\",s.STATION_GB as \"num\" " +
            "FROM TBL_BASE_STATION_INFO s " +
            "WHERE s.STATION_GB LIKE 'G006031%' " +
            "UNION ALL " +
            "SELECT g.DEVICE_NAME as \"name\",null as \"stationId\",g.DEVICE_ID as \"num\" " +
            "FROM TBL_GANTRY_INFO g )c on c.\"num\" = a.ORIGIN " +
            "LEFT JOIN ( " +
            "SELECT s.STATION_NAME as \"name\",s.STATION_GB as \"num\" " +
            "FROM TBL_BASE_STATION_INFO s " +
            "WHERE s.STATION_GB LIKE 'G006031%' " +
            "UNION ALL " +
            "SELECT g.DEVICE_NAME as \"name\",g.DEVICE_ID as \"num\" " +
            "FROM TBL_GANTRY_INFO g )d on d.\"num\" = a.DESTINATION " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and r.START_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and r.END_TIME &lt;= #{endDate} " +
            "</when> "+
            "ORDER BY START_TIME DESC"+
            "</script>"})
    List<Map> roadFlowStatis(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
