package com.pingok.vocational.service.primaryCar;

import java.util.Date;
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

    /**
     * 获取两客一危车辆GPS信息
     * @param vehPlate
     * @return
     */
    List<Map> selectPrimaryGpsInfo(String vehPlate,Date startTime,Date endTime);

    /**
     * 获取车辆GPS轨迹
     * @param vehPlate
     * @return
     */
    List<Map> getVehGpsList(String vehPlate);

    /**
     *超限车辆信息查询
     * @param vehPlate
     * @return
     */
    List<Map> selectOwInfo(String vehPlate,Date checkStartTime, Date checkEndTime);
}
