package com.pingok.monitor.service.device;

import com.alibaba.fastjson.JSONArray;

public interface IStatusService {
    void serverStatus(JSONArray array);
    void pingStatus(JSONArray array);
    void switchStatus(JSONArray array);
    void firewallStatus(JSONArray array);
}
