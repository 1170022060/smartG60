package com.pingok.event.service.radarCamera;

import com.alibaba.fastjson.JSONObject;

public interface IRadarCameraService {
    void handleTraffic(JSONObject data);
}
