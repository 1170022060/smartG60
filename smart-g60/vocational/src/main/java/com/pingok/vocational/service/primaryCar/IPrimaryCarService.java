package com.pingok.vocational.service.primaryCar;

import java.util.List;
import java.util.Map;

/**
 * @author lal
 */
public interface IPrimaryCarService {

    /**
     * 获取两客一危车辆信息
     * @param vehPlate
     * @return
     */
    List<Map> getPrimaryVehInfo(String vehPlate);

    /**
     * 获取车辆轨迹信息
     * @param vehPlate
     * @return
     */
    List<Map> getVehTrailInfo(String vehPlate);

    /**
     * 获取车辆运单信息
     * @param vehPlate
     * @return
     */
    List<Map> getWayBillInfo(String vehPlate);
}
