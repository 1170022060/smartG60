package com.pingok.vocational.mapper.assist;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblRoadForecastMapper {
    @Select({"<script>" +
            "SELECT  " +
            "r.VELOCITY as \"velocity\", " +
            "r.FLOW as \"flow\", " +
            "case when  r.CONGESTION <![CDATA[ >= ]]> 0 AND r.CONGESTION <![CDATA[ < ]]> 2 then '畅通' when r.CONGESTION <![CDATA[ >= ]]> 2 AND r.CONGESTION <![CDATA[ < ]]> 3 then '缓行' " +
            "when r.CONGESTION <![CDATA[ >= ]]> 3 AND r.CONGESTION <![CDATA[ < ]]> 4 then '拥堵' when r.CONGESTION <![CDATA[ >= ]]> 4 AND r.CONGESTION <![CDATA[ <= ]]> 5 then '严重拥堵' end as \"jamLevel\", " +
            "to_char( r.START_TIME, 'yyyy-mm-dd hh24:mi:ss' ) as \"startTime\", " +
            "to_char( r.END_TIME, 'yyyy-mm-dd hh24:mi:ss' ) as \"endTime\", " +
            "a.\"ORIGIN\" as \"origin\", " +
            "a.MESSAGE as \"message\" " +
            "FROM TBL_ROAD_FORECAST r " +
            "LEFT JOIN TBL_ROAD_INFO a on a.ROAD_ID = r.ROAD_ID " +
            "where 1=1 " +
            "<when test='startDate != null'> " +
            " and r.START_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and r.END_TIME &lt;= #{endDate} " +
            "</when> "+
            "ORDER BY r.START_TIME DESC"+
            "</script>"})
    List<Map> roadForecast(@Param("startDate") Date startDate, @Param("endDate")Date endDate);
}
