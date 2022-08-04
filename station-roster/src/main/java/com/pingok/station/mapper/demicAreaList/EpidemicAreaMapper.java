package com.pingok.station.mapper.demicAreaList;

import com.pingok.station.domain.demicAreaList.EpidemicArea;

public interface EpidemicAreaMapper {
    /**
     * 中高风险地区名单入库
     *
     * @param epidemicArea
     * @return 结果
     */
    public int insertDemicArea(EpidemicArea epidemicArea);

    /**
     * 清除中高风险地区名单
     *
     * @return 结果
     */
    public void deleteAll();
}
