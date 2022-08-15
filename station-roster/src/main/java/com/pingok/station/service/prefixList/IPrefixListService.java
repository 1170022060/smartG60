package com.pingok.station.service.prefixList;

import com.pingok.station.domain.prefixList.vo.PrefixVo;

public interface IPrefixListService {
    /**
     * 中高风险地区车牌前缀名单全量下载、入库
     * @param version 版本号
     */
    void prefixAreaList(String version);

    void insertPrefixArea(PrefixVo prefixVo, String version);

    void test(String version);
}
