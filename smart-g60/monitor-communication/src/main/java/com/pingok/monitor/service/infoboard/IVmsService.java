package com.pingok.monitor.service.infoboard;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoboard.SansiParkingPubInfo;

public interface IVmsService {
    JSONObject publish(String pubInfo);
    boolean publish(SansiParkingPubInfo parkInfo);
    void collect(String devList);
    void notifyResult(JSONObject result);
}
