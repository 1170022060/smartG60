package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONObject;

public interface ITblOwService {
    /**
     * 超限车辆数据
     * @param result
     */
    void getOwInfo(JSONObject result);
}
