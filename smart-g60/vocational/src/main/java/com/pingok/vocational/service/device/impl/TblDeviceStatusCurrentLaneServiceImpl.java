package com.pingok.vocational.service.device.impl;

import com.pingok.vocational.domain.device.TblDeviceStatusCurrentLane;
import com.pingok.vocational.mapper.device.TblDeviceStatusCurrentLaneMapper;
import com.pingok.vocational.service.device.TblDeviceStatusCurrentLaneService;
import com.ruoyi.common.core.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 车道设备当前状态表 服务层处理
 *
 * @author ruoyi
 */
@Service
public class TblDeviceStatusCurrentLaneServiceImpl implements TblDeviceStatusCurrentLaneService {
    @Autowired
    private TblDeviceStatusCurrentLaneMapper tblDeviceStatusCurrentLaneMapper;

    @Override
    public List<TblDeviceStatusCurrentLane> selectDeviceStatusCurrentLane(String deviceId) {
        Example example = new Example(TblDeviceStatusCurrentLane.class);
        Example.Criteria criteria = example.createCriteria();

        if(StringUtils.isNotBlank(deviceId)) {
            criteria.andEqualTo("deviceId", deviceId);
        }
        return tblDeviceStatusCurrentLaneMapper.selectByExample(example);
    }
}
