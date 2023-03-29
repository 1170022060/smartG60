package com.pingok.monitor.service.infoboard;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoboard.SanSiInfo;

public interface IFcmsService {
    JSONObject sendData(SanSiInfo sanSiData);
}
