package com.pingok.monitor.service.leise;

import com.alibaba.fastjson.JSONObject;

public interface ILeiseService {
    Boolean handleObject(JSONObject data);
    Boolean handleEvent(JSONObject data);
}
