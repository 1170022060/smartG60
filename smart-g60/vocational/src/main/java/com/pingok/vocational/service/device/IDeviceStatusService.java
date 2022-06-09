package com.pingok.vocational.service.device;

/**
 * 设备当前状态表 服务层
 *
 * @author ruoyi
 */
public interface IDeviceStatusService {
    /**
     * 新增设备状态
     *
     * @param deviceId 设备主键ID
     * @return 结果
     */
    public int insertDeviceStatus(Long deviceId);
}
