package com.pingok.dingtalk.service.base;

import com.pingok.dingtalk.daemon.base.DeviceInfo;

import java.util.List;

/**
 * 设施设备信息表-钉钉 服务类
 *
 * @author chenwg
 * @since 2023-04-13
 */
public interface IDeviceInfoService {

    /**
     * 列表查询
     *
     * @param deviceInfo
     * @return
     */
    List<DeviceInfo> listByEntity(DeviceInfo deviceInfo);

    /**
     * 详情
     *
     * @param id
     * @return
     */
    DeviceInfo selectById(Short id);

    /**
     * 保存
     *
     * @param deviceInfo
     * @return
     */
    int saveDeviceInfo(DeviceInfo deviceInfo);

    /**
     * 修改
     *
     * @param deviceInfo
     * @return
     */
    int modify(DeviceInfo deviceInfo);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    int deleteById(Short id);
}
