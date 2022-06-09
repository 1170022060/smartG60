package com.pingok.monitor.service.leise;

import com.alibaba.fastjson.JSONObject;

public interface ILeiseStoreService {
    void saveObject(JSONObject objectData);
    void saveEvent(JSONObject eventData);
}
