package com.pingok.vocational.mapper.road;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblRoadStatisEventMapper {

    @Select({"<script>" +
            "SELECT " +
            "case when a.TYPE =1 then '拥堵等级' when a.TYPE =2 then '超速'  " +
            "when a.TYPE=3 then '数据连续缺失' when a.TYPE=4 then '设备异常' end as \"eventType\", " +
            "a.MESSAGE as \"message\", " +
            "b.MESSAGE as \"roadName\", " +
            "case when  a.CONGESTION <![CDATA[ >= ]]> 0 AND a.CONGESTION <![CDATA[ < ]]> 2 then '畅通' when a.CONGESTION <![CDATA[ >= ]]> 2 AND a.CONGESTION <![CDATA[ < ]]> 3 then '缓行' " +
            "when a.CONGESTION <![CDATA[ >= ]]> 3 AND a.CONGESTION <![CDATA[ < ]]> 4 then '拥堵' when a.CONGESTION <![CDATA[ >= ]]> 4 AND a.CONGESTION <![CDATA[ <= ]]> 5 then '严重拥堵' end as \"congestion\", " +
            "a.EXCEED_CAR as \"vehPlate\", " +
            "case when a.PREDICTION ='true' then '预测事件' else '历史或实时事件' end as \"prediction\", " +
            "a.START_TIME as \"startTime\", " +
            "a.END_TIME as \"endTime\" " +
            "FROM TBL_ROAD_STATIS_EVENT_INFO a " +
            "LEFT JOIN TBL_ROAD_INFO b on a.ROAD_ID = b.ROAD_ID " +
            "where 1=1" +
            "<when test='vehPlate != null'> " +
            " and a.EXCEED_CAR like '%' || #{vehPlate} || '%' " +
            "</when>" +
            "<when test='startTime != null'> " +
            " and to_date(a.START_TIME,'yyyy-mm-dd hh24:mi:ss') &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and to_date(a.END_TIME,'yyyy-mm-dd hh24:mi:ss') &lt;= #{endTime} " +
            "</when>"+
            "ORDER BY a.START_TIME DESC"+
            "</script>"})
    List<Map> selectRoadStatisEvent(@Param("vehPlate")String vehPlate, @Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
