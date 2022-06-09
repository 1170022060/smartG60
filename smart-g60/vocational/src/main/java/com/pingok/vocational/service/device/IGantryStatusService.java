package com.pingok.vocational.service.device;

/**
 * 门架设备状态表 服务层
 *
 * @author ruoyi
 */
public interface IGantryStatusService {
    /**
     * 新增门架设备状态
     *
     * @param deviceId 门架设备主键ID
     * @return 结果
     */
    public int insertGantryStatus(Long deviceId);
}
