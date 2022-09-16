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
}
