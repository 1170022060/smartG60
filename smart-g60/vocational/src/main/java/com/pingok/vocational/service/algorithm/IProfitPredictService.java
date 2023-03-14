package com.pingok.vocational.service.algorithm;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IProfitPredictService {

    /**
     * 收益预测
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    List<Map> profitPredict(Date startTime,Date endTime,Integer type);

    /**
     * 收益预测汇总
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> getProfitPredTotal(Date startTime,Date endTime);
}
