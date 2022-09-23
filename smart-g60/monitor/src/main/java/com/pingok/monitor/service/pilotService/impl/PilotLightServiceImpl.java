package com.pingok.monitor.service.pilotService.impl;

import com.alibaba.fastjson.JSONArray;
import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.pingok.monitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.monitor.service.pilotService.IPilotLightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Slf4j
@Service
public class PilotLightServiceImpl implements IPilotLightService {

    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;

    @Override
    public List<TblDeviceStatus> getRtStatus(Integer roadId) {
        Example example = new Example(TblDeviceStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("device_type", 12);
        if(roadId != null) {
            criteria.andEqualTo("deviceId", roadId);
        }
        List<TblDeviceStatus> list = tblDeviceStatusMapper.selectByExample(example);
        return list;
    }
}
