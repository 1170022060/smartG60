package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;

/**
 * 建筑碳排放业务接口
 */
public interface TblAlgBuildCarbonEmissionService {

    /**
     * 保存建筑碳排放记录
     *
     * @param tblAlgBuildCarbonEmission
     * @return Boolean
     */
    Boolean saveAlgBuildCarbonEmission(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission);

    /**
     * 修改建筑碳排放记录
     *
     * @param tblAlgBuildCarbonEmission
     * @return Boolean
     */
    Boolean modifyAlgBuildCarbonEmission(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission);

}
