package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;

/**
 * 碳排放业务接口
 */
public interface TblAlgVehCarbonEmissionService {

    /**
     * 按时获取门架过车数据，保存车辆碳排放记录
     */
    void saveAlgVehCarbonEmission();

    /**
     * 按时间查询车辆碳排放
     * @param queryTime
     * @return
     */
    TblAlgVehCarbonEmission getAlgVehCarbonEmission(String queryTime);
}
