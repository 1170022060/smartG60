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

    @Select({"<script>" +
            "select tfi.ID as \"fieldId\"," +
            "tfi.FIELD_NAME as \"fieldName\" from TBL_EVENT_PASSENGER_FLOW tep " +
            "left join TBL_DEVICE_INFO tdi on tdi.ID=tep.UBI_SOURCE_ID " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tdi.FIELD_BELONG " +
            "where tep.UBI_START_TIME &gt;= #{time} " +
            "group by tfi.ID,tfi.FIELD_NAME" +
            "</script>"})
    List<Map> field(@Param("time") Long time);

    @Select("select tfi.ID as \"deviceId\"," +
            "tdi.DEVICE_NAME as \"deviceName\" from TBL_EVENT_PASSENGER_FLOW tep " +
            "left join TBL_DEVICE_INFO tdi on tdi.ID=tep.UBI_SOURCE_ID " +
            "left join TBL_FIELD_INFO tfi on tfi.ID=tdi.FIELD_BELONG " +
            "where FIELD_ID = #{fieldId} and tep.UBI_START_TIME &gt;= #{time}")
    List<Map> device(@Param("time") Long time, @Param("fieldId") Long fieldId);

    @Select("select sum(UI_GET_IN_PEOS) as \"dailyTotal\" from TBL_EVENT_PASSENGER_FLOW " +
            "where UBI_SOURCE_ID = #{deviceId} and UBI_START_TIME &gt;= #{time} ")
    Integer dailyTotal(@Param("time") Long time, @Param("deviceId") Long deviceId);

    @Select("select UI_IN_PEOS as \"actualFlow\" from TBL_EVENT_PASSENGER_FLOW " +
            "where UBI_SOURCE_ID = #{deviceId} and UBI_START_TIME &gt;= #{time} and rownum=1 " +
            "order by UBI_START_TIME desc ")
    Integer actualFlow(@Param("time") Long time, @Param("deviceId") Long deviceId);

    @Select("select max(UI_IN_PEOS) as \"peakFlow\" from TBL_EVENT_PASSENGER_FLOW " +
            "where UBI_SOURCE_ID = #{deviceId} and UBI_START_TIME &gt;= #{time} ")
    Integer peakFlow(@Param("time") Long time, @Param("deviceId") Long deviceId);

    @Select("select AVG(UI_IN_PEOS) as \"peakFlow\" from TBL_EVENT_PASSENGER_FLOW " +
            "where UBI_SOURCE_ID = #{deviceId} and UBI_START_TIME &gt;= (#{time}-240000) and UBI_START_TIME &lt;= #{time} ")
    Integer avgFlow(@Param("time") Long time, @Param("deviceId") Long deviceId);

    @Select("select sum(UI_GET_IN_PEOS) as \"peakFlow\" from TBL_EVENT_PASSENGER_FLOW " +
            "where UBI_SOURCE_ID = #{deviceId} and UBI_START_TIME &gt;= (#{time}-60000) and UBI_START_TIME &lt;= #{time} ")
    Integer hourFlow(@Param("time") Long time, @Param("deviceId") Long deviceId);


}
