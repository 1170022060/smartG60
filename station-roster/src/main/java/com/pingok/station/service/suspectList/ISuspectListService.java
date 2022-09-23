package com.pingok.station.service.suspectList;

import com.pingok.station.domain.suspectList.vo.SuspectVo;

import java.util.List;

public interface ISuspectListService {
    /**
     * 疑似违法车辆名单全量下载、入库
     * @param version 版本号
     */
    void suspectList(String version);

    void insertSuspect(List<SuspectVo> list, String version);

    void test(String version);
}
