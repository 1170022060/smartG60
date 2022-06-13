package com.pingok.algorithm.carbonEmission.service;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;

import java.util.List;

/**
 * 建筑碳排放业务接口
 */
public interface TblAlgBuildCarbonEmissionService {

    /**
     * 查询碳排放列表
     * @param tblAlgBuildCarbonEmission
     * @return
     */
    List<TblAlgBuildCarbonEmission> listByBean(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission);

    /**
     * 查询碳排放详情
     * @param tblAlgBuildCarbonEmission
     * @return
     */
    TblAlgBuildCarbonEmission selectByBean(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission);

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
