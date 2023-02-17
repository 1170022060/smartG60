package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

import java.text.ParseException;

public interface ITblTruckOWService {
    /**
     * 开始发起kafka消息
     */
    void collect();

    /**
     * 货车超重分布数据
     * @param result
     */
    void getTruckOWInfo(JSONArray result);
}
