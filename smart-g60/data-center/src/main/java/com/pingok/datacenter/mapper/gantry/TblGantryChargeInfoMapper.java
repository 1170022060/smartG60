package com.pingok.datacenter.mapper.gantry;

import com.pingok.datacenter.domain.gantry.TblGantryChargeInfo;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    List<Map> selectChargeFlow(@Param("table") String table, @Param("chargeId") String chargeId, @Param("time") String transDate);
}
