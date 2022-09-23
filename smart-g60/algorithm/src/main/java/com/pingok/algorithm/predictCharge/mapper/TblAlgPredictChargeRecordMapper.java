package com.pingok.algorithm.predictCharge.mapper;

import com.pingok.algorithm.predictCharge.entity.TblAlgPredictChargeRecord;
import com.ruoyi.common.core.mapper.CommonRepository;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TblAlgPredictChargeRecordMapper extends CommonRepository<TblAlgPredictChargeRecord> {

    /**
     * 查询最新预测收益记录
     */
    @Select("<script>" +
            " SELECT " +
            " SUM(PRE_FLOW) AS preFlow, " +
            " SUM(PRE_THEORY_CHARGE) AS preTheoryCharge, " +
            " SUM(PRE_REAL_CHARGE) AS preRealCharge " +
            " FROM " +
            " TBL_ALG_PREDICT_CHARGE_RECORD " +
            " WHERE " +
            " RECORD_DATE = #{currentDate} " +
            " <if test = 'chargeIntervalId != null and chargeIntervalId != \"\"'> " +
            " and CHARGE_INTERVAL_ID = #{chargeIntervalId} " +
            " </if> " +
            "</script>")
    TblAlgPredictChargeRecord getLatestPredictChargeRecord(@Param("currentDate") String currentDate, @Param("chargeIntervalId") String chargeIntervalId);
}
