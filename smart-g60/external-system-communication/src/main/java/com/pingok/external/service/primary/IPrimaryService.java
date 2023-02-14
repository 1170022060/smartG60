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

    /**
     * 收费站经纬度信息
     * @param result
     */
    void getStationLLInfo(JSONArray result);

    /**
     * 超限流量、超限率时间对比数据
     * @param result
     */
    void getOLFlowAndRate(JSONArray result);

    /**
     * 超限重量占比对比数据
     * @param result
     */
    void getOLWeightAndRate(JSONArray result);

    /**
     * 按治超站统计车货总重大于 100 吨超限量信息
     * @param result
     */
    void getTotalWeightOver100(JSONArray result);

    /**
     * 申请大件运输车辆信息
     * @param result
     */
    void getLargeTransportInfo(JSONArray result);

}
