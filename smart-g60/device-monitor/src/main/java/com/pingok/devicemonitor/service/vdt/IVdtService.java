package com.pingok.devicemonitor.service.vdt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IVdtService {
    void collect();
    void notifyResult(JSONArray result);
}
