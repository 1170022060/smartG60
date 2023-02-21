package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface ITblOLFlowService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 超限流量分布数据
     * @param result
     */
    void getOLFlowInfo(JSONArray result);
}
