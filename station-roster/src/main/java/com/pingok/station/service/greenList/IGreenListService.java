package com.pingok.station.service.greenList;

import com.pingok.station.domain.greenList.vo.GreenVo;

import java.util.List;

public interface IGreenListService {
    /**
     * 绿通预约名单全量下载、入库
     * @param version 版本号
     */
    void greenList(String version);

    void insertGreen(List<GreenVo> list);

}
