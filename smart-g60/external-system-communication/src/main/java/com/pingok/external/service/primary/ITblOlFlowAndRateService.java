package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface ITblOlFlowAndRateService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 超限流量、超限率时间对比数据
     * @param result
     */
    void getOlFlowAndRateInfo(JSONArray result);
}
