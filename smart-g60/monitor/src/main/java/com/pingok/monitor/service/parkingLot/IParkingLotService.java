package com.pingok.monitor.service.parkingLot;

import java.util.List;
import java.util.Map;

/**
 * 停车场 业务层
 *
 * @author qiumin
 */
public interface IParkingLotService {

    /**
     * 流量统计
     * @return
     */
    List<Map>  flowStatistics();
    /**
     * 根据场地id查询停车场信息
     * @param fieldNum 场地编号
     * @return
     */
    List<Map> findByFieldNum(String fieldNum);
}
