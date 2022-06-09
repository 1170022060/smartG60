package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;

/**
 * 植物碳排放业务接口
 */
public interface TblAlgPlantCarbonEmissionService {

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
