package com.pingok.station.service.bulkList;


import com.pingok.station.domain.bulkList.BulkRecord;
import com.pingok.station.domain.bulkList.vo.BulkVo;

import java.util.List;

public interface IBulkListService {
    /**
     * 大件运输预约名单全量下载、入库
     * @param version 版本号
     */
    void bulkList(String version);

    void insertBulk(List<BulkVo> list);

}
