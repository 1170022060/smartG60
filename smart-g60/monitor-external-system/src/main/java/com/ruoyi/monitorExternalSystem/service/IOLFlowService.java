package com.ruoyi.monitorExternalSystem.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


public interface IOLFlowService {
    /**
     *  接收到kafka信号后去获取数据
     * @param params
     */
    void getOLFlowInfo(JSONObject params);
}
