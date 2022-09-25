package com.pingok.devicemonitor.service.infoboard;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;

import java.util.List;

public interface IInfoBoardService {
    int publish(JSONObject pubInfo);

    int notifyResult(JSONObject result);

    int collect();

    int notifyPing(JSONArray result);
}
