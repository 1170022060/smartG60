package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface ITblLargeTransportService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 申请大件运输车辆信息数据
     * @param result
     */
    void getLargeTransport(JSONArray result);
}
