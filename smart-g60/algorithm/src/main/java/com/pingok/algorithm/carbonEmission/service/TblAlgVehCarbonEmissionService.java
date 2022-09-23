package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;

import java.util.List;

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

    /**
     * 查询车辆碳排放列表
     * @param tblAlgVehCarbonEmission
     * @return
     */
    List<TblAlgVehCarbonEmission> listByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission);

    /**
     * 查询车辆碳排放详情
     * @param tblAlgVehCarbonEmission
     * @return
     */
    TblAlgVehCarbonEmission selectByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission);
}
