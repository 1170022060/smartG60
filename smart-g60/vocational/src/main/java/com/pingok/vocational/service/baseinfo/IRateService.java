package com.pingok.vocational.service.baseinfo;

import java.util.Date;
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
     * @param inStationName 入口站名
     * @param exStationId 出口站Id
     * @param vehClass 车型
     * @param versionNum 版本号
     * @param provId 省份
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 最小费率
     */
    public List<Map> selectRate(String inStationName, String exStationId, Integer vehClass, String versionNum, String provId, Date startTime, Date endTime);

    /**
     * 根据对应最小费率表主键ID查询分省明细
     *
     * @param rateId 对应最小费率表主键ID
     * @return 分省明细
     */
    public List<Map> selectRateProv(Long rateId);

    public List<Map> selectInStation();

    public List<Map> selectVersionNum();

    List<Map> selectRateContrast(String enId, String exId, Integer vehClass);
}
