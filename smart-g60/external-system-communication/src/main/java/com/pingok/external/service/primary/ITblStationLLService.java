package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface ITblStationLLService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 收费站经纬度信息数据
     * @param result
     */
    void getStationLLInfo(JSONArray result);
}
