package com.pingok.devicemonitor.service.vdt;

import com.alibaba.fastjson.JSONObject;

public interface IVdtService {
    void collect();
    void notifyResult(JSONObject result);
}
