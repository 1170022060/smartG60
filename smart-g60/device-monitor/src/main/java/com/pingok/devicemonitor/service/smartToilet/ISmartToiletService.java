package com.pingok.devicemonitor.service.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletCubicle;

public interface ISmartToiletService {


    /**
     * 滚动文字
     */
    void marqueeText();

    /**
     * 天气同步服务
     */
    void weather();

    /**
     * 校时服务
     */
    void timeCalibration();

    void sensorData(JSONObject sensorData);

    /**
     * 更新厕所坑位状态
     * @param tblSmartToiletCubicle
     * @return
     */
    int updateToiletStatus(TblSmartToiletCubicle tblSmartToiletCubicle);
}
