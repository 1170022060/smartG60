package com.pingok.devicemonitor.service.infoboard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;

import java.util.List;

public interface IInfoBoardService {
    void publish(JSONObject pubInfo);

    void notifyResult(JSONObject result);

    void collect();

    void notifyPing(JSONArray result);
}
