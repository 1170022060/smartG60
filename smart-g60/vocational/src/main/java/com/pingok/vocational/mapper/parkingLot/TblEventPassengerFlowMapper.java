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

    @Select("select sum(ENTRY) as \"dailyTotal\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} ")
    Integer dailyTotal(@Param("time") String time,@Param("areaId") Integer areaId);

    @Select("select IN_AMOUNT as \"actualFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} and rownum=1 " +
            "order by HOUR desc ")
    Integer actualFlow(@Param("time") String time,@Param("areaId") Integer areaId);

    @Select("select max(IN_AMOUNT) as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and WORK_DATE = #{time} ")
    Integer peakFlow(@Param("time") String time, @Param("areaId") Integer areaId);

    @Select("select AVG(IN_AMOUNT) as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and rownum<=4 " +
            " and to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss')<to_date(#{time} ||' ' ||#{hour} ||':00:00','yyyy-mm-dd hh24:mi:ss') " +
            " order by to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss') desc ")
    Integer avgFlow(@Param("areaId") Integer areaId,@Param("time") String time, @Param("hour") Integer hour);

    @Select("select IN_AMOUNT as \"peakFlow\" from TBL_EVENT_PASSENGER_STATISTICS " +
            "where AREA_ID = #{areaId} and rownum=1 " +
            " and to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss')<to_date(#{time} ||' ' ||#{hour} ||':00:00','yyyy-mm-dd hh24:mi:ss') " +
            " order by to_date(WORK_DATE ||' '||HOUR ||':00:00','yyyy-mm-dd hh24:mi:ss') desc ")
    Integer hourFlow(@Param("areaId") Integer areaId,@Param("time") String time, @Param("hour") Integer hour);


}
