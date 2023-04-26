package com.pingok.monitor.mapper.parkingLot;

import com.pingok.monitor.domain.parkingLot.TblParkingVehicleInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 *  数据层
 *
 * @author qiumin
 */
@Mapper
public interface ParkingLotMapper extends CommonRepository<TblParkingVehicleInfo> {
    @Select("SELECT tpl.ID as \"id\",tpl.REGION_NAME as \"regionName\", tpl.REGION_NUM as \"regionNum\",tpl.TOTAL as \"total\",tpl.SURPLUS as \"surplus\"  FROM TBL_PARKING_LOT tpl JOIN TBL_FIELD_INFO tfi ON tfi.ID = tpl.FIELD_ID WHERE tfi.FIELD_NUM = #{fieldNum}")
    List<Map> findByFieldNum(@Param("fieldNum") String fieldNum);

    @Select("SELECT " +
            "pvi.ID AS \"id\", " +
            "pvi.VEH_PLATE AS \"vehPlate\", " +
            "sdd.DICT_LABEL AS \"vehClass\", " +
            "sdd1.DICT_LABEL AS \"vehColor\"," +
            "case pvi.STATUS when 0 then '未驶离' when 1 then '正常驶离' when 2 then '系统判定驶离' end as \"status\", " +
            "to_char(pvi.EN_TIME,'yyyy-mm-dd hh24:mi:ss') AS \"enTime\"  " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO pvi " +
            "JOIN  SYS_DICT_DATA sdd ON sdd.DICT_VALUE = pvi.VEH_CLASS  " +
            "AND sdd.DICT_TYPE = 'veh_class' " +
            "JOIN  SYS_DICT_DATA sdd1 ON sdd1.DICT_VALUE = pvi.VEH_CLASS  " +
            "AND sdd1.DICT_TYPE = 'veh_color'  " +
            "WHERE " +
            "pvi.EX_TIME IS NULL  " +
            "AND CEIL( ( SYSDATE - pvi.EN_TIME ) * 24 ) > (SELECT CONFIG_VALUE FROM  SYS_CONFIG sc WHERE sc.CONFIG_KEY='parking.timeout') " +
            "AND pvi.PARKING_ID = #{parkingId} order by pvi.EN_TIME ")
    List<Map> findByParkingId(@Param("parkingId") Long parkingId);

    @Select("SELECT " +
            "count( tpvi.id ) AS \"carNum\", " +
            "sum( " +
            "CASE " +
            "tpvi.VEH_CLASS  " +
            "WHEN 0 THEN " +
            "2  " +
            "WHEN 1 THEN " +
            "2  " +
            "WHEN 2 THEN " +
            "2  " +
            "WHEN 3 THEN " +
            "2  " +
            "WHEN 4 THEN " +
            "10  " +
            "WHEN 5 THEN " +
            "20  " +
            "WHEN 6 THEN " +
            "30  " +
            "WHEN 7 THEN " +
            "2  " +
            "WHEN 8 THEN " +
            "2  " +
            "WHEN 9 THEN " +
            "10 ELSE 2  " +
            "END  " +
            ") AS \"peopleNum\", " +
            "nvl(teps.MAN,0) AS \"man\", " +
            "nvl(teps.WOMAN,0) AS \"woman\", " +
            "CASE " +
            "tpl.FIELD_ID  " +
            "WHEN 3940 THEN " +
            "'南区'  " +
            "WHEN 3941 THEN " +
            "'北区'  " +
            "END AS \"field\"  " +
            "FROM " +
            "TBL_PARKING_VEHICLE_INFO tpvi " +
            "JOIN TBL_PARKING_LOT tpl ON tpvi.PARKING_ID = tpl.ID " +
            "LEFT JOIN ( " +
            "SELECT " +
            "FIELD_ID, " +
            "sum( decode( AREA_ID, 3, IN_AMOUNT, 0 ) ) AS MAN, " +
            "sum( decode( AREA_ID, 4, IN_AMOUNT, 0 ) ) AS WOMAN  " +
            "FROM " +
            "TBL_EVENT_PASSENGER_STATISTICS  " +
            "WHERE " +
            "AREA_ID IN ( 3, 4 )  " +
            "AND HOUR = TO_CHAR( SYSDATE, 'hh24' )  " +
            "GROUP BY " +
            "FIELD_ID  " +
            ") teps ON teps.FIELD_ID = tpl.FIELD_ID  " +
            "WHERE " +
            "tpvi.EX_TIME IS NULL  " +
            "GROUP BY " +
            "tpl.FIELD_ID, " +
            "teps.MAN, " +
            "teps.WOMAN")
    List<Map> flowStatistics();
}
