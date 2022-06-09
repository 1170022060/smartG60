package com.pingok.devicemonitor.service.heartbeat;

import com.alibaba.fastjson.JSONObject;

public interface IHeartbeatService {
    boolean ping(String ip);

    JSONObject serverSnmp(String ip);
}
