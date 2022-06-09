package com.pingok.devicemonitor.service.infoboard;

import com.alibaba.fastjson.JSONObject;

public interface IInfoBoardService {
    void publish(JSONObject vmsPublishInfo);
}
