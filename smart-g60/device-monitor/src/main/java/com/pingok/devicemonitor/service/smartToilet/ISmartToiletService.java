package com.pingok.devicemonitor.service.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletCubicle;

public interface ISmartToiletService {
    void sensorData(JSONObject sensorData);

    /**
     * 更新厕所坑位状态
     * @param tblSmartToiletCubicle
     * @return
     */
    int updateToiletStatus(TblSmartToiletCubicle tblSmartToiletCubicle);
}
