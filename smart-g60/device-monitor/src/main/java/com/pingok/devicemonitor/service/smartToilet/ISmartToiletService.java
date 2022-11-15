package com.pingok.devicemonitor.service.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletCubicle;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletInfo;
import io.swagger.models.auth.In;

public interface ISmartToiletService {

    /**
     * 更新侧位状态
     * @param index
     * @param status
     */
    void setSensor(String serNum,Integer index, Integer status);

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
    TblSmartToiletCubicle updateToiletStatus(TblSmartToiletCubicle tblSmartToiletCubicle);


    /**
     * 根据ID查询厕所信息
     * @param id
     * @return
     */
    TblSmartToiletInfo selectById(Long id);
}
