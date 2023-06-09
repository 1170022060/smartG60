package com.pingok.devicemonitor.mapper.gantry;

import com.pingok.devicemonitor.domain.gantry.TblGantryChargeInfo;
import com.pingok.devicemonitor.domain.gantry.vo.ChargeFlowModel;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TblGantryChargeInfoMapper extends CommonRepository<TblGantryChargeInfo> {

//    @Select("SELECT " +
//            "CHARGE_UNIT_ID as \"chargeIntervalId\", " +
//            "#{time} as \"statisticsDate\", " +
//            "count(DISTINCT VEHICLE_PLATE || SUBSTR(TRANS_TIME, 0, 16)) as \"flow\" " +
//            "FROM #{table} WHERE 1=1 and TRANS_DATE = #{time} " +
//            "<when test='chargeId != null'> " +
//            "and CHARGE_UNIT_ID = #{chargeId}" +
//            "</when>" +
//            "GROUP BY CHARGE_UNIT_ID " +
//            "ORDER BY CHARGE_UNIT_ID")
    List<ChargeFlowModel> selectChargeFlowList(@Param("table") String table, @Param("chargingUnitId") String chargingUnitId, @Param("startDate") String startDate, @Param("endDate") String endDate);

    ChargeFlowModel selectChargeFlow(@Param("table") String table, @Param("chargingUnitId") String chargingUnitId, @Param("statisticsDate") String statisticsDate);
}
