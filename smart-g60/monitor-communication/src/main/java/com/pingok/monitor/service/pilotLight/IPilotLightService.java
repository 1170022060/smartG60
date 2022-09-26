package com.pingok.monitor.service.pilotLight;

import com.alibaba.fastjson.JSONObject;

public interface IPilotLightService {
    void updateStatus();

    void commandSend(JSONObject body);
}
