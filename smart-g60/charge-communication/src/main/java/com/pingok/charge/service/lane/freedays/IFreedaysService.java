package com.pingok.charge.service.lane.freedays;

import com.alibaba.fastjson.JSONObject;

public interface IFreedaysService {
    Boolean send(JSONObject data);
    Boolean sendToDass(JSONObject data);
}
