package com.pingok.vocational.service.parkingLot;

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
}
