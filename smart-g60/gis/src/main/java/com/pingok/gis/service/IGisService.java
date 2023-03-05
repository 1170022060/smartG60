package com.pingok.gis.service;


/**
 * GIS 服务层
 *
 * @author qiumin
 */
public interface IGisService {

    /**
     * 更新状态
     *
     * @param code
     * @param status
     * @param type
     */
    void updateStatus(String code, Integer status, String type);

    void UpdateRoadStatus(Long gisId,Integer status);
}
