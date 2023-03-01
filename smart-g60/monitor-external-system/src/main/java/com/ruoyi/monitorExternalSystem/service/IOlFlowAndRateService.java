package com.ruoyi.monitorExternalSystem.service;

import com.alibaba.fastjson.JSONObject;

public interface IOlFlowAndRateService {
    /**
     * 接收到kafka信号后去获取数据
     *
     */
    void getOlFlowAnsRateInfo(JSONObject params);
}
