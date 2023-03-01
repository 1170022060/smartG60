package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface ITblPrimaryGpsService {

    /**
     * 两客一危GPS数据
     * @param result
     */
    void getPrimaryGps(JSONObject result);
}
