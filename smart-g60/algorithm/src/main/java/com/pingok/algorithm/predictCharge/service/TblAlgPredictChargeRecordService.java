package com.pingok.algorithm.predictCharge.service;

import com.pingok.algorithm.predictCharge.entity.TblAlgPredictChargeRecord;

/**
 * 收益预测记录业务类
 */
public interface TblAlgPredictChargeRecordService {

    /**
     * 定时计算当天的收费预测记录
     */
    void autoCurrentDayPredictCharge() throws Exception;

    /**
     * 输入日期范围预测收益
     * @param chargeIntervalId 收费区间编号
     * @param startDate 开始日期
     * @param endDate 结束日期
     */
    TblAlgPredictChargeRecord getPredictChargeRecordByDate(String chargeIntervalId, String startDate, String endDate);
}
