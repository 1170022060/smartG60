package com.pingok.monitor.service.device.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.monitor.service.device.IDeviceStatusService;
import com.ruoyi.common.security.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 设备 服务层处理
 *
 * @author qiumin
 */
@Service
public class DeviceStatusServiceImpl implements IDeviceStatusService {
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;



    @Override
    public List<Map> list(Long deviceCategory,String deviceName,String deviceId) {
        List<Map> maps = tblDeviceStatusMapper.list(deviceCategory,deviceName,deviceId, SecurityUtils.getUserId());
        for (Map m : maps) {
            if (m.get("statusDetails") != null) {
                m.put("statusDetails", JSONObject.parseObject(String.valueOf(m.get("statusDetails"))));
            }
        }
        return maps;
    }
}
