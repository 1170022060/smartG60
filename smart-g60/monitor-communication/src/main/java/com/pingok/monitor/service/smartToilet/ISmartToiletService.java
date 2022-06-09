package com.pingok.monitor.service.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import org.springframework.web.bind.annotation.RequestBody;

public interface ISmartToiletService {
    void sensorData(JSONObject sensorData);
}
