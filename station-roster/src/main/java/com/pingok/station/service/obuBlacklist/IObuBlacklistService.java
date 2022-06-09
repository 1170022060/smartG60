package com.pingok.station.service.obuBlacklist;

import com.pingok.station.domain.cardBlacklist.vo.BlackVo;
import com.pingok.station.domain.obuBlacklist.vo.BlackObuVo;

import java.util.List;

public interface IObuBlacklistService {
    /**
     * 下载OBU黑名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载OBU黑名单全量
     * @param version 版本号
     */
    void all(String version);

    /**
     * OBU黑名单全量解压入库
     * @param version 版本号
     */
    void unzipAll(String version);

    void insert(List<BlackObuVo> list, String version);

    void jedisInsert(List<BlackObuVo> list, String version);

}
