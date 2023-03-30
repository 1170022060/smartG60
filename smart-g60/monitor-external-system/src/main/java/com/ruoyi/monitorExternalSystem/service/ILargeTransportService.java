package com.ruoyi.monitorExternalSystem.service;

import com.alibaba.fastjson.JSONObject;

public interface ILargeTransportService {
    /**
     * 接收到kafka信号后去获取数据
     *
     */
    void getLargeTransport(JSONObject params);
}
