package com.pingok.station.service.demicAreaList;

import com.pingok.station.domain.demicAreaList.vo.DemicVo;

import java.util.List;

public interface IDemicAreaListService {
    /**
     * 中高风险地区名单全量下载、入库
     * @param version 版本号
     */
    void demicAreaList(String version);

    void insertDemicArea(List<DemicVo> list,String version);

    void test(String version);
}
