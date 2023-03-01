package com.pingok.vocational.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblSharGtdResSenderMapper {
    @Select({"<script>" +
            "SELECT " +
            "a.\"gantryId\", " +
            "a.\"stasticBatch\", " +
            "a.\"workDate\", " +
            "a.\"etcLocal\", " +
            "a.\"etcElse\", " +
            "a.\"etcTotal\", " +
            "a.\"mtcSingle\", " +
            "a.\"mtcTrans\", " +
            "a.\"mtcTotal\", " +
            "a.\"etcFlow\" + a.\"mtcFlow\" as \"totalSum\", " +
            "NVL(b.\"lprFlow\",0) as \"license\" " +
            "FROM( " +
            "SELECT  " +
            "GANTRY_ID AS \"gantryId\", " +
            "HOUR_BATCH_NO AS \"stasticBatch\", " +
            "to_char(TRANS_TIME, 'yyyy-mm-dd hh24:mi:ss') AS \"transTime\", " +
            "count(case when CPU_NET_ID = '31' AND MEDIA_TYPE = 1 AND TRADE_RESULT = 0 THEN 1 ELSE null END ) AS \"etcLocalFlow\", " +
            "count(case when CPU_NET_ID != '31' AND MEDIA_TYPE = 1 AND TRADE_RESULT = 0 THEN 1 ELSE null END) AS \"etcTransProvinceFlow\", " +
            "count(case when CPU_NET_ID = '31' AND MEDIA_TYPE = 1 AND TRADE_RESULT = 0 THEN 1 ELSE null END) + count( " +
            "case when CPU_NET_ID != '31' AND MEDIA_TYPE = 1 AND TRADE_RESULT = 0 THEN 1 ELSE null END) AS \"etcFlow\", " +
            "count(case when PROVINCE_NUM_AFTER = 1 AND MEDIA_TYPE = 2 AND TRADE_RESULT = 0 THEN 1 ELSE null END) AS \"mtcLocalFlow\", " +
            "count(case when PROVINCE_NUM_AFTER > 1 AND MEDIA_TYPE = 2 AND TRADE_RESULT = 0 THEN 1 ELSE null END) AS \"mtcTransProvinceFlow\", " +
            "count(case when PROVINCE_NUM_AFTER = 1 AND MEDIA_TYPE = 2 AND TRADE_RESULT = 0 THEN 1 ELSE null END) + count( " +
            "case when PROVINCE_NUM_AFTER > 1 AND MEDIA_TYPE = 2 AND TRADE_RESULT = 0 THEN 1 ELSE null END) AS \"mtcFlow\" " +
            "FROM TBL_SHAR_GTD_RES_SENDER_${year} " +
            "where 1=1 " +
            "<when test= 'gantryId !=null'> " +
            "and GANTRY_ID like '%' || #{gantryId} || '%' " +
            "</when>" +
            "<when test='startDate != null'> " +
            "and TRANS_TIME &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            "and TRANS_TIME &lt;= #{endDate} " +
            "</when>" +
            "group by GANTRY_ID,TRANS_TIME,HOUR_BATCH_NO)a " +
            "LEFT JOIN (SELECT GANTRY_ID as \"gantryId\", count(*) as \"lprFlow\",HOUR_BATCH_NO as \"stasticBatch\" from TBL_SHAR_GVID_RES_SENDER_${year}  " +
            "group by GANTRY_ID,HOUR_BATCH_NO)b " +
            "on b.\"gantryId\" = a.\"gantryId\" AND b.\"stasticBatch\" = a.\"stasticBatch\" " +
            "</script>"})
    List<Map> getGantryFlow(@Param("year") String year, @Param("gantryId") String gantryId,
                            @Param("startDate")Date startDate,@Param("endDate")Date endDate);
}
