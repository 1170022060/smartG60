package com.pingok.station.service.cardBlacklist;

import com.pingok.station.domain.cardBlacklist.vo.BlackVo;

import java.util.List;

public interface ICardBlacklistService {
    /**
     * 下载卡黑名单增量
     * @param version 版本号
     */
    void increment(String version);

    /**
     * 下载卡黑名单全量
     * @param version 版本号
     */
    void all(String version);

    /**
     * 卡黑名单全量解压入库
     * @param version 版本号
     */
    void unzipAll(String version);

    void insert(List<BlackVo> list, String version);

    void jedisInsert(List<BlackVo> list, String version);

    Boolean findByCardId(String cardId);

    void test(String version);
}
