package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblParkingStatistics;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_PARKING_STATISTICS 数据层
 *
 * @author xia
 */
public interface TblParkingStatisticsMapper extends CommonRepository<TblParkingStatistics> {

    @Select("select tfi.ID as \"fieldId\"," +
            "tfi.FIELD_NAME as \"fieldName\" from TBL_PARKING_STATISTICS tps " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tps.FIELD_ID " +
            "where tps.DAY = #{date} " +
            "group by tfi.ID,tfi.FIELD_NAME" )
    List<Map> trafficSum(@Param("date") Date date);

    @Select("select sum(ENTER) as \"dailyCumulative\" from TBL_PARKING_STATISTICS " +
            "where FIELD_ID = #{fieldId} and DAY = #{date} and VEH_TYPE = #{vehType}")
    Integer trafficDaily(@Param("date") Date date, @Param("fieldId") Long fieldId,@Param("vehType") Integer vehType);

    @Select("select \"hour\",\"current\" from " +
            "(select HOUR as \"hour\"," +
            "CURRENT_NUM as \"current\"  from TBL_PARKING_STATISTICS " +
            "where DAY = #{date} " +
            "and FIELD_ID = #{fieldId} " +
            "and VEH_TYPE = #{vehType} " +
            "and rownum<=8" +
            "order by HOUR desc) " +
            "order by \"hour\" ")
    List<Map> trafficStatistics(@Param("date") Date date, @Param("fieldId") Long fieldId, @Param("vehType") Integer vehType);

    @Select({"<script>" +
            "SELECT " +
            "tfi.FIELD_NAME AS \"fieldName\", " +
            "<when test='statisticsType == 1'> " +
            "to_char(tps.DAY,'yyyy-mm-dd') || ' ' ｜｜tps.HOUR || '时' AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            "to_char(tps.DAY,'yyyy-mm-dd') AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            "substr(to_char(tps.DAY,'yyyy-mm-dd'),1,7) AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            "substr(to_char(tps.DAY,'yyyy-mm-dd'),1,4) AS \"day\"," +
            "</when>"+
            "case tps.VEH_TYPE when 1 then '客车' when 2 then '货车' end AS \"vehType\"," +
            "sum(ENTER) AS \"flow\" " +
            "FROM " +
            "TBL_PARKING_STATISTICS tps " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = tps.FIELD_ID " +
            "WHERE 1=1 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "<when test='vehType != null'> " +
            "and tps.VEH_TYPE = #{vehType}" +
            "</when>"+
            "<when test='startDate != null'> " +
            " and tps.DAY &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and tps.DAY &lt;= #{endDate} " +
            "</when>"+
            " group by tfi.FIELD_NAME,case tps.VEH_TYPE when 1 then '客车' when 2 then '货车' end " +
            "<when test='statisticsType == 1'> " +
            " ,to_char(tps.DAY,'yyyy-mm-dd') || ' ' ｜｜tps.HOUR || '时' " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            " ,to_char(tps.DAY,'yyyy-mm-dd') " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            " ,substr(to_char(tps.DAY,'yyyy-mm-dd'),1,7) " +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            " ,substr(to_char(tps.DAY,'yyyy-mm-dd'),1,4) " +
            "</when>"+
            "</script>"})
    List<Map> traffic(@Param("fieldNum") String fieldNum,@Param("vehType") Integer vehType ,@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("statisticsType") Integer statisticsType);

    @Select({"<script>" +
            "SELECT " +
            "sum(ENTER) AS \"enter\", " +
            "sum(OUT) AS \"out\" " +
            "FROM " +
            "TBL_PARKING_STATISTICS tps " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = tps.FIELD_ID " +
            "WHERE 1=1 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "<when test='vehType != null'> " +
            "and tps.VEH_TYPE = #{vehType}" +
            "</when>"+
            "<when test='startDate != null'> " +
            " and tps.DAY &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and tps.DAY &lt;= #{endDate} " +
            "</when>"+
            "</script>"})
    Map trafficCount(@Param("fieldNum") String fieldNum,@Param("vehType") Integer vehType ,@Param("startDate") Date startDate,@Param("endDate") Date endDate);

}
