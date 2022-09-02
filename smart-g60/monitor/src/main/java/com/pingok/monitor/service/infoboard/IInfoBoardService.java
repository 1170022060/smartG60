package com.pingok.monitor.service.infoboard;

import com.alibaba.fastjson.JSONObject;

public interface IInfoBoardService {
    int publish(JSONObject pubInfo);
}
