package com.pingok.monitor.mapper.parkingLot;

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
public interface ParkingLotMapper {
    @Select("SELECT tpl.ID as \"id\",tpl.REGION_NAME as \"regionName\", tpl.REGION_NUM as \"regionNum\",tpl.TOTAL as \"total\",tpl.SURPLUS as \"surplus\"  FROM TBL_PARKING_LOT tpl JOIN TBL_FIELD_INFO tfi ON tfi.ID = tpl.FIELD_ID WHERE tfi.FIELD_NUM = #{fieldNum}")
    List<Map> findByFieldNum(@Param("fieldNum") String fieldNum);

    @Select("SELECT " +
            "pvi.ID AS \"id\", " +
            "pvi.VEH_PLATE AS \"vehPlate\", " +
            "sdd.DICT_LABEL AS \"vehClass\", " +
            "sdd1.DICT_LABEL AS \"vehColor\", " +
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
            "AND pvi.PARKING_ID = #{parkingId}")
    List<Map> findByParkingId(@Param("parkingId") Long parkingId);
}
