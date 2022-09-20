package com.pingok.charge.service.gantry;

import com.alibaba.fastjson.JSONObject;

public interface IGantryV2XService {

    boolean sendEvent(JSONObject data);
}
