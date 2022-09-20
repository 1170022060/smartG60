package com.pingok.devicemonitor.service.infoboard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface IInfoBoardService {
    int publish(JSONObject pubInfo);

    int notifyResult(JSONArray result);
}
