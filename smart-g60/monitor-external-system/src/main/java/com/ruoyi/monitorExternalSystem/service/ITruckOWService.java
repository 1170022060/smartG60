package com.ruoyi.monitorExternalSystem.service;

import com.alibaba.fastjson.JSONObject;

public interface ITruckOWService {
        /**
         * 接收到kafka信号后去获取数据
         *
         * @param params
         */
        void getTruckOWInfo(JSONObject params);
}
