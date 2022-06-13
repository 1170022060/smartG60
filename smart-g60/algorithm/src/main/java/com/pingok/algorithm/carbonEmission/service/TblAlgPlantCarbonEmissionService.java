package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;

import java.util.List;

/**
 * 植物碳排放业务接口
 */
public interface TblAlgPlantCarbonEmissionService {

    /**
     * 查询植物碳排放列表
     * @param tblAlgPlantCarbonEmission
     * @return
     */
    List<TblAlgPlantCarbonEmission> listByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission);

    /**
     * 查询植物碳排放详情
     * @param tblAlgPlantCarbonEmission
     * @return
     */
    TblAlgPlantCarbonEmission selectByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission);

    /**
     * 保存植物碳排放记录
     *
     * @param tblAlgPlantCarbonEmission
     * @return Boolean
     */
    Boolean saveAlgPlantCarbonEmission(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission);

    /**
     * 修改植物碳排放记录
     *
     * @param tblAlgPlantCarbonEmission
     * @return Boolean
     */
    Boolean modifyAlgPlantCarbonEmission(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission);
}
