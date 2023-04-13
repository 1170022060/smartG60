package com.pingok.dingtalk.service.base.impl;

import com.pingok.dingtalk.daemon.base.DeviceInfo;
import com.pingok.dingtalk.mapper.base.DeviceInfoMapper;
import com.pingok.dingtalk.service.base.IDeviceInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;



/**
 * 设施设备信息表-钉钉 服务实现类
 *
 * @author chenwg
 * @since 2023-04-13
 */
@Service
public class DeviceInfoServiceImpl implements IDeviceInfoService {

    @Resource
    private DeviceInfoMapper deviceInfoMapper;

    /**
     * 列表查询
     *
     * @param deviceInfo
     * @return
     */
    @Override
    public List<DeviceInfo> listByEntity(DeviceInfo deviceInfo) {
        return deviceInfoMapper.select(deviceInfo);
    }

    /**
     * 详情
     *
     * @param id
     * @return
     */
    @Override
    public DeviceInfo selectById(Short id) {
        return deviceInfoMapper.selectByPrimaryKey(id);
    }

    /**
     * 保存
     *
     * @param deviceInfo
     * @return
     */
    @Override
    public int saveDeviceInfo(DeviceInfo deviceInfo) {
        return deviceInfoMapper.insert(deviceInfo);
    }

    /**
     * 修改
     *
     * @param deviceInfo
     * @return
     */
    @Override
    public int modify(DeviceInfo deviceInfo) {
        return deviceInfoMapper.updateByPrimaryKey(deviceInfo);
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public int deleteById(Short id) {
        return deviceInfoMapper.deleteByPrimaryKey(id);
    }
}
