package com.pingok.vocational.mapper.road;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
            "case when a.CONGESTION='[0,2)' then '畅通' when a.CONGESTION='[2,3)' then '缓行'  " +
            "when a.CONGESTION='[3,4)' then '拥堵' when a.CONGESTION='[4,5]' then '严重拥堵' end as \"congestion\", " +
            "a.EXCEED_CAR as \"vehPlate\", " +
            "case when a.PREDICTION ='true' then '预测事件' else '历史或实时事件' end as \"prediction\", " +
            "a.START_TIME as \"startDate\", " +
            "a.END_TIME as \"endDate\" " +
            "FROM TBL_ROAD_STATIS_EVENT_INFO a " +
            "LEFT JOIN TBL_ROAD_INFO b on a.ROAD_ID = b.ROAD_ID " +
            "</script>"})
    List<Map> selectRoadStatisEvent();
}
