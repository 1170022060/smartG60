package com.pingok.vocational.service.baseinfo;

import java.util.List;
import java.util.Map;

/**
 * 最小费率 业务层
 *
 * @author ruoyi
 */
public interface IRateService {

    /**
     * 根据模糊入口站名、出口站Id、车型查询最小费率
     *
     * @param stationName 入口站名
     * @param exStationId 出口站Id
     * @param vehClass 车型
     * @return 最小费率
     */
    public List<Map> selectRate(String stationName,String exStationId, Integer vehClass);

    /**
     * 根据对应最小费率表主键ID查询分省明细
     *
     * @param rateId 对应最小费率表主键ID
     * @return 分省明细
     */
    public List<Map> selectRateProv(Long rateId);
}
