package com.pingok.external.mapper.roadDoctor;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TblRoadDoctorMapper {
    @Select({"<script>" +
            "SELECT " +
            "trd.ID AS \"id\", " +
            "trd.M_QUESTION_DEGREE AS \"mQuestionDegree\", " +
            "trd.P_ZHUANG_HAO AS \"pZhuangHao\", " +
            "trd.P_PICURL AS \"pPicurl\", " +
            "trdt.QUEST_NAME AS \"questName\", " +
            "CASE " +
            "trdr.CR_TYPE  " +
            "WHEN 1 THEN " +
            "'轻度病害'  " +
            "WHEN 2 THEN " +
            "'中度病害'  " +
            "WHEN 3 THEN " +
            "'重度病害' ELSE '未分级'  " +
            "END AS \"crType\", " +
            "CASE " +
            "trd.STATUS  " +
            "WHEN 1 THEN " +
            "'新增'  " +
            "WHEN 2 THEN " +
            "'已处理'  " +
            "END AS \"status\", " +
            "trpi.B_ROAD_NAME AS \"bRoadName\", " +
            "to_char(trpi.B_TIME, 'yyyy-mm-dd hh24:mi:ss') AS \"bTime\"  " +
            "FROM " +
            "TBL_ROAD_DISEASE trd " +
            "JOIN TBL_ROAD_DISEASE_TYPE trdt ON trdt.QUEST_ID = trd.QUEST_ID " +
            "JOIN TBL_ROAD_DISEASE_REPORT trdr ON trdr.CR_ID = trdt.CR_ID " +
            "JOIN TBL_ROAD_PATROL_INSPECTION trpi ON trpi.B_ID = trdr.CR_BID " +
            "where 1=1 " +
            "<when test='questName != null'> " +
            "and trdt.QUEST_NAME like '%' || #{questName} || '%' " +
            "</when>"+
            "<when test='pZhuangHao != null'> " +
            "and trd.P_ZHUANG_HAO like '%' || #{pZhuangHao} || '%' " +
            "</when>"+
            "<when test='startTime != null'> " +
            "and trpi.B_TIME &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            "and trpi.B_TIME &lt;= #{endTime} " +
            "</when>"+
            "ORDER BY trd.ID DESC " +
            "</script>"})
    List<Map> list(@Param("questName") String questName, @Param("pZhuangHao") String pZhuangHao, @Param("startTime") Date startTime, @Param("endTime")  Date endTime);
}
