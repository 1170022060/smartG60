package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface ITblOlWeightAndRateService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 超限重量占比对比数据
     * @param result
     */
    void getOlWeightAndRateInfo(JSONArray result);
}
