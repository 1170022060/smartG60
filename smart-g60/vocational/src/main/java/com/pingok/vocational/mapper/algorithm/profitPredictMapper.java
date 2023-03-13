package com.pingok.vocational.mapper.algorithm;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface profitPredictMapper {
    @Select({"<script>" +
            "select " +
            "<when test='type == 1'> " +
            "SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) as \"predDate\", " +
            "</when>"+
            "<when test='type == 2'> " +
            "SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) as \"predDate\", " +
            "</when>"+
            "<when test='type == 3'> " +
            "substr(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'),0,7) AS \"predDate\"," +
            "</when>"+
            "sum(PRED_FLOW) as \"predFlow\"," +
            "sum(PRED_PROFIT) as \"predProfit\" from TBL_ROAD_PROFIT_PRED " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and PRED_DATE &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and PRED_DATE &lt;= #{endTime} " +
            "</when>"+
            " group by " +
            "<when test='type == 1'> " +
            " SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 2'> " +
            " SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 3'> " +
            " SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 7) " +
            "</when>"+
            "<when test='type == 1'> " +
            " order by SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 2'> " +
            " order by SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 10) " +
            "</when>"+
            "<when test='type == 3'> " +
            " order by SUBSTR(to_char(PRED_DATE,'yyyy-mm-dd hh24:mi:ss'), 0, 7) " +
            "</when>"+
            "</script>"})
    List<Map> profitPred(@Param("startTime")Date startTime,@Param("endTime")Date endTime,@Param("type")Integer type);

    @Select({"<script>" +
            "select " +
            "sum(PRED_FLOW) as \"predFlow\"," +
            "sum(PRED_PROFIT) as \"predProfit\" from TBL_ROAD_PROFIT_PRED " +
            "where 1=1 " +
            "<when test='startTime != null'> " +
            " and PRED_DATE &gt;= #{startTime} " +
            "</when>"+
            "<when test='endTime != null'> " +
            " and PRED_DATE &lt;= #{endTime} " +
            "</when>"+
            "</script>"})
    List<Map> getProfitPredTotal(@Param("startTime")Date startTime,@Param("endTime")Date endTime);
}
