package com.pingok.devicemonitor.service.gantry;

import com.alibaba.fastjson.JSONObject;

public interface IGantryUpperService {
    void handleBaseInfoUpload(JSONObject body);
    void handleSpecialEventUpload(JSONObject body);
    void handleTghbu(JSONObject body);

}
