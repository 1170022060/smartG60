package com.pingok.vocational.service.roster;

import java.util.List;
import java.util.Map;

/**
 * 车辆通行记录 业务层
 *
 * @author ruoyi
 */
public interface IVehicleRecordService {
    /**
     * 通过车牌查询车辆通行记录
     *
     * @param vehPlate 车牌
     * @return 车辆通行记录
     */
    List<Map> selectVehicleRecord(String vehPlate);
}
