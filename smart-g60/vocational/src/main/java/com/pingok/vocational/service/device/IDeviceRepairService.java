package com.pingok.vocational.service.device;

import java.util.List;
import java.util.Map;

/**
 * 设备维修记录 业务层
 *
 * @author ruoyi
 */
public interface IDeviceRepairService {

    /**
     * 根据设备编码查询设备维修记录
     *
     * @param deviceId 设备编码
     * @return 设备维修记录
     */
    List<Map> selectDeviceRepair(Long deviceId);
}
