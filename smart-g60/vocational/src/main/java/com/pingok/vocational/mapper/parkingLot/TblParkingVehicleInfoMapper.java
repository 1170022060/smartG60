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
}
