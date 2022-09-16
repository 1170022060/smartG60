package com.pingok.vocational.service.parkingLot;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 停车场 业务层
 *
 * @author qiumin
 */
public interface IParkingLotService {
    /**
     * 车辆已驶离
     *
     * @param id 主键id
     */
    void driveAway(Long id);

    /**
     * 更新剩余车位
     * @param id 停车区域id
     * @param surplus 剩余车位
     */
    void updateSurplus(Long id, Integer surplus);

    List<Map> trafficChange(Date date);
    List<Map> parkingPlace();
    List<Map> passengerFlow(Date date) throws ParseException;
}
