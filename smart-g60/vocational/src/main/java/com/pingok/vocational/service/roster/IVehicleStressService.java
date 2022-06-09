package com.pingok.vocational.service.roster;

import java.util.List;
import java.util.Map;

/**
 * 重点车辆清单 业务层
 *
 * @author ruoyi
 */
public interface IVehicleStressService {

    /**
     * 通过车牌查询重点车辆清单
     *
     * @param vehType 车辆种类
     * @param vehType 车牌
     * @return 重点车辆清单
     */
    List<Map> selectVehicleStress(Integer vehType,String vehPlate);
}
