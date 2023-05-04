package com.pingok.vocational.mapper.report;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface TblVdHistoryRecordMapper {
    @Select({"<script>" +
            "SELECT  " +
            "a.DEVICE_NAME as \"deviceName\", " +
            "a.PILE_NO as \"pileNo\"," +
            "case vd.DIRECTION when 1 then '上行' when 2 then '下行' end as \"direction\", " +
            "<when test='statisticsType == 1'> " +
            "SUBSTR(vd.COLLECT_TIME, 0, 13)|| '时' as \"time\", " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            "SUBSTR(vd.COLLECT_TIME, 0, 10) as \"time\", " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            "substr(vd.COLLECT_TIME,0,7) AS \"time\"," +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            "SUBSTR(vd.COLLECT_TIME, 0, 4) AS \"time\"," +
            "</when>"+
            "sum(vd.VOLUME) as \"totalFlow\" " +
            "FROM TBL_VD_HISTORY_RECORD vd  " +
            "LEFT JOIN TBL_DEVICE_INFO a on a.DEVICE_ID = vd.DEVICE_ID " +
            "where 1=1" +
            "<when test='deviceName != null'> " +
            " and vd.DEVICE_ID = #{deviceName} " +
            "</when>"+
            "<when test='startDate != null'> " +
            " and to_date( vd.COLLECT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and to_date( vd.COLLECT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) &lt;= #{endDate} " +
            "</when> "+
            " group by a.DEVICE_NAME,a.PILE_NO,vd.DIRECTION " +
            "<when test='statisticsType == 1'> " +
            " ,SUBSTR(vd.COLLECT_TIME, 0, 13) || '时' " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            " ,SUBSTR(vd.COLLECT_TIME, 0, 10) " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            " ,SUBSTR(vd.COLLECT_TIME, 0, 7) " +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            " ,SUBSTR(vd.COLLECT_TIME, 0, 4) " +
            "</when>" +
            "<when test='statisticsType == 1'> " +
            " order by SUBSTR(vd.COLLECT_TIME, 0, 13) || '时' desc " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            " order by SUBSTR(vd.COLLECT_TIME, 0, 10) desc  " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            " order by SUBSTR(vd.COLLECT_TIME, 0, 7) desc " +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            " order by SUBSTR(vd.COLLECT_TIME, 0, 4) desc" +
            "</when>" +
            "</script>"})
    List<Map> selectVdHistory(@Param("deviceName") String deviceName,
                              @Param("statisticsType") Integer statisticsType,
                              @Param("startDate")Date startDate,@Param("endDate") Date endDate);

    @Select({"<script>" +
            "SELECT sum(vd.VOLUME) as \"totalFlow\" from TBL_VD_HISTORY_RECORD vd "+
            "where 1=1" +
            "<when test='deviceName != null'> " +
            " and vd.DEVICE_ID = #{deviceName} " +
            "</when>"+
            "<when test='startDate != null'> " +
            " and to_date( vd.COLLECT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and to_date( vd.COLLECT_TIME, 'yyyy-mm-dd hh24:mi:ss' ) &lt;= #{endDate} " +
            "</when> "+
            "</script>"})
    int getTotal(@Param("deviceName") String deviceName,
                 @Param("statisticsType") Integer statisticsType,
                 @Param("startDate")Date startDate,@Param("endDate") Date endDate);

    @Select({"<script>" +
            "select CONCAT(PILE_NO,case when DIRECTION=1 then '_上行' ELSE '_下行' end)as \"pileNo\",DEVICE_ID as \"deviceId\" " +
            "FROM TBL_DEVICE_INFO " +
            "where DEVICE_TYPE = 11 " +
            "UNION ALL " +
            "SELECT CONCAT(PILE_NO,'_上行') as pileNo,DEVICE_ID as deviceId FROM TBL_DEVICE_INFO where DEVICE_ID in ('VD4','VD5','VD6','VD7') " +
            "ORDER BY \"pileNo\" " +
            "</script>"})
    List<Map> selectPileNo();
}
