package com.pingok.monitor.service.infoboard;

import com.alibaba.fastjson.JSONObject;

public interface IVmsService {
    JSONObject publish(String pubInfo);
    void collect(String devList);
    void notifyResult(JSONObject result);
}
