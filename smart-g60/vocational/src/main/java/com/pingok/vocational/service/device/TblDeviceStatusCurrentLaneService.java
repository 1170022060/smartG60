package com.pingok.vocational.service.device;

import com.pingok.vocational.domain.device.TblDeviceStatusCurrentLane;

import java.util.List;

/**
 * 车道设备当前状态表 服务层
 *
 * @author ruoyi
 */
public interface TblDeviceStatusCurrentLaneService {
    /**
     * 通过设备编码查询车道设备当前状态
     *
     * @param deviceId 设备编码
     * @return 车道设备当前状态
     */
    List<TblDeviceStatusCurrentLane> selectDeviceStatusCurrentLane(String deviceId);
}
