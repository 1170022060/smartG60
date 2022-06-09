package com.pingok.event.service.leise;

import com.alibaba.fastjson.JSONObject;

public interface ILeiseService {
    Boolean handleObject(JSONObject data);
    Boolean handleEvent(JSONObject data);
}
