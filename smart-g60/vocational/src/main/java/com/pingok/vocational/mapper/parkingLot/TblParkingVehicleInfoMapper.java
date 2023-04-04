package com.pingok.vocational.mapper.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblParkingVehicleInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * TBL_PARKING_VEHICLE_INFO 数据层
 *
 * @author qiu
 */
public interface TblParkingVehicleInfoMapper extends CommonRepository<TblParkingVehicleInfo> {

    @Select("SELECT \"current\"," +
            "case when \"current\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.flow')*1.2 then '重度拥堵'" +
            "when \"current\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.flow') then '中度拥堵'" +
            "when \"current\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.flow')*0.9 then '轻度拥堵'" +
            "else '正常' end as \"degree\" from " +
            "(SELECT " +
            "count(1) AS \"current\" " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO pvi " +
            "LEFT JOIN TBL_PARKING_LOT tpl ON tpl.ID = pvi.PARKING_ID  " +
            "WHERE " +
            "pvi.EX_TIME IS NULL  " +
            "AND tpl.FIELD_ID = #{fieldId})")
    Map trafficCurrent(@Param("fieldId") Long fieldId);


    @Select("SELECT " +
            "count(1) AS \"overtime\" " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO " +
            "WHERE " +
            "EX_TIME IS NULL  " +
            "AND CEIL( ( SYSDATE - EN_TIME ) * 24 ) > (SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.timeout') " +
            "AND PARKING_ID = #{parkingId}")
    Map overtime(@Param("parkingId") Long parkingId);

    @Select("SELECT \"humanFlow\"," +
            "case when \"humanFlow\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.humanFlow')*1.2 then '重度拥堵'" +
            "when \"humanFlow\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.humanFlow') then '中度拥堵'" +
            "when \"humanFlow\">=(SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.humanFlow')*0.9 then '轻度拥堵'" +
            "else '正常' end as \"degree\" from " +
            "(SELECT " +
            "sum(case when VEH_CLASS_SUB in(0,1,2,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,31,33,34,37,38,39,40) then 1 " +
            "when VEH_CLASS_SUB in(3,7,8,9,10,29,30,37) then 2 " +
            "when VEH_CLASS_SUB in(4) then 10 " +
            "when VEH_CLASS_SUB in(5) then 20" +
            "when VEH_CLASS_SUB in(6,11,12) then 30 else 0 end)" +
            " AS \"humanFlow\" " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO pvi " +
            "LEFT JOIN TBL_PARKING_LOT tpl ON tpl.ID = pvi.PARKING_ID  " +
            "WHERE " +
            "pvi.EX_TIME IS NULL  " +
            "AND tpl.FIELD_ID = #{fieldId})")
    Map humanFlow(@Param("fieldId") Long fieldId);


    @Select({"<script>" +
            "SELECT " +
            "pvi.ID AS \"id\", " +
            "pvi.VEH_PLATE AS \"vehPlate\", " +
            "sdd.DICT_LABEL AS \"vehClass\", " +
            "sdd1.DICT_LABEL AS \"vehColor\", " +
            "tfi.FIELD_NAME || ':' || tpl.REGION_NAME AS \"place\", " +
            "to_char(pvi.EN_TIME,'yyyy-mm-dd hh24:mi:ss') AS \"enTime\"  " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO pvi " +
            "JOIN  SYS_DICT_DATA sdd ON sdd.DICT_VALUE = pvi.VEH_CLASS  " +
            "AND sdd.DICT_TYPE = 'park_veh_class' " +
            "JOIN  SYS_DICT_DATA sdd1 ON sdd1.DICT_VALUE = pvi.VEH_COLOR  " +
            "AND sdd1.DICT_TYPE = 'park_veh_color'  " +
            "JOIN TBL_PARKING_LOT tpl ON tpl.ID = pvi.PARKING_ID " +
            "JOIN TBL_FIELD_INFO tfi ON tfi.ID = tpl.FIELD_ID " +
            "WHERE " +
            "pvi.EX_TIME IS NULL  " +
            "AND CEIL( ( SYSDATE - pvi.EN_TIME ) * 24 ) > (SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.timeout') " +
            "<when test='fieldNum != null'> " +
            "and tfi.FIELD_NUM = #{fieldNum}" +
            "</when>"+
            "<when test='regionName != null'> " +
            "and tpl.REGION_NAME = #{regionName}" +
            "</when>" +
            "order by pvi.EN_TIME DESC "+
            "</script>"})
    List<Map> overtimeInfo(@Param("fieldNum") String fieldNum,@Param("regionName") String regionName);
}
