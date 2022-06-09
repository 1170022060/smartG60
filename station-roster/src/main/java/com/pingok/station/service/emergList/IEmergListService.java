package com.pingok.station.service.emergList;

import com.pingok.station.domain.emergList.vo.EmgVo;

import java.util.List;

public interface IEmergListService {
    /**
     * 下载抢险救灾名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载抢险救灾名单全量
     * @param version 版本号
     */
    void all(String version);

    /**
     * 抢险救灾名单全量解压入库
     * @param version 版本号
     */
    void unzipAll(String version);

    void insertAll(List<EmgVo> list, String version);

}
