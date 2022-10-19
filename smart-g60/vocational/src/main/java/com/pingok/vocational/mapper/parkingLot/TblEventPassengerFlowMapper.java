package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblEventPassengerFlow;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TBL_EVENT_PASSENGER_FLOW 数据层
 *
 * @author xia
 */
public interface TblEventPassengerFlowMapper extends CommonRepository<TblEventPassengerFlow> {

    @Select("select ID as \"fieldId\"," +
            "FIELD_NAME as \"fieldName\" from TBL_FIELD_INFO " +
            "where TYPE=4 ")
    List<Map> field();

    @Select("select NVL(sum(ENTRY),0) as \"dailyTotal\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} and FIELD_ID= #{fieldId} ")
    Integer dailyTotal(@Param("time") String time,@Param("areaId") Integer areaId,@Param("fieldId") Long fieldId);

    @Select("select NVL(IN_AMOUNT,0) as \"actualFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} and FIELD_ID= #{fieldId} and rownum=1 " +
            "order by HOUR desc ")
    Integer actualFlow(@Param("time") String time,@Param("areaId") Integer areaId,@Param("fieldId") Long fieldId);

    @Select("select NVL(max(IN_AMOUNT),0) as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} and FIELD_ID= #{fieldId} ")
    Integer peakFlow(@Param("time") String time, @Param("areaId") Integer areaId,@Param("fieldId") Long fieldId);

    @Select("select NVL(AVG(IN_AMOUNT),0) as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and rownum<=4 and FIELD_ID= #{fieldId} " +
            " and to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss')<to_date(#{time} ||' ' ||#{hour} ||':00:00','yyyy-mm-dd hh24:mi:ss') " +
            " order by to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss') desc ")
    Integer avgFlow(@Param("areaId") Integer areaId,@Param("time") String time, @Param("hour") Integer hour,@Param("fieldId") Long fieldId);

    @Select("select NVL(IN_AMOUNT,0) as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId}  and FIELD_ID= #{fieldId} and rownum=1 " +
            " and to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss')<to_date(#{time} ||' ' ||#{hour} ||':00:00','yyyy-mm-dd hh24:mi:ss') " +
            " order by to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss') desc ")
    Integer hourFlow(@Param("areaId") Integer areaId,@Param("time") String time, @Param("hour") Integer hour,@Param("fieldId") Long fieldId);

    @Select({"<script>" +
            "SELECT " +
            "tfi.FIELD_NAME AS \"fieldName\", " +
            "<when test='statisticsType == 1'> " +
            "eps.WORK_DATE || ' ' ｜｜eps.HOUR || '时' AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            "eps.WORK_DATE AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            "substr(eps.WORK_DATE,1,7) AS \"day\"," +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            "substr(eps.WORK_DATE,1,4) AS \"day\"," +
            "</when>"+
            "case eps.AREA_ID " +
            "when 1 then '商铺' " +
            "when 2 then '超市' " +
            "when 3 then '男厕' " +
            "when 4 then '女厕' " +
            "when 5 then '艺海棠' " +
            "when 6 then '司机之家' end AS \"area\"," +
            "sum(eps.ENTRY) AS \"flow\" " +
            "FROM " +
            "TBL_EVENT_PASSENGER_STATISTICS eps " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = eps.FIELD_ID " +
            "WHERE 1=1 " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "<when test='areaId != null'> " +
            "and eps.AREA_ID = #{areaId}" +
            "</when>"+
            "<when test='startDate != null'> " +
            " and to_date(eps.WORK_DATE,'yyyy-mm-dd') &gt;= #{startDate} " +
            "</when>"+
            "<when test='endDate != null'> " +
            " and to_date(eps.WORK_DATE,'yyyy-mm-dd') &lt;= #{endDate} " +
            "</when>"+
            " group by tfi.FIELD_NAME," +
            "case eps.AREA_ID " +
            "when 1 then '商铺' " +
            "when 2 then '超市' " +
            "when 3 then '男厕' " +
            "when 4 then '女厕' " +
            "when 5 then '艺海棠' " +
            "when 6 then '司机之家' end " +
            "<when test='statisticsType == 1'> " +
            " ,eps.WORK_DATE || ' ' ｜｜eps.HOUR || '时' " +
            "</when>"+
            "<when test='statisticsType == 2'> " +
            " ,eps.WORK_DATE " +
            "</when>"+
            "<when test='statisticsType == 3'> " +
            " ,substr(eps.WORK_DATE,1,7) " +
            "</when>"+
            "<when test='statisticsType == 4'> " +
            " ,substr(eps.WORK_DATE,1,4) " +
            "</when>"+
            "</script>"})
    List<Map> humanFlow(@Param("fieldNum") String fieldNum,@Param("areaId") Integer areaId ,@Param("startDate") Date startDate,@Param("endDate") Date endDate,@Param("statisticsType") Integer statisticsType);

}
