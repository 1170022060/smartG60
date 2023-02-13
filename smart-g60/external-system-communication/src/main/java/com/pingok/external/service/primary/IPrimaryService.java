package com.pingok.external.service.primary;

import com.alibaba.fastjson.JSONArray;

public interface IPrimaryService {

    /**
     * 两客一危 GPS 数据
     * @param result
     */
    void getPrimaryGps(JSONArray result);

    /**
     * 货车超重分布数据
     * @param result
     */
    void getTruckOverWeight(JSONArray result);

    /**
     * 超限车辆信息数据
     * @param result
     */
    void getOWInfo(JSONArray result);

    /**
     * 超限流量分布数据
     * @param result
     */
    void getOLFlowInfo(JSONArray result);

}
