package com.pingok.monitor.service.pilotService.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.pingok.monitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.monitor.mapper.pilotLight.PilotLightMapper;
import com.pingok.monitor.service.pilotService.IPilotLightService;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class PilotLightServiceImpl implements IPilotLightService {

    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;

    @Autowired
    private PilotLightMapper pilotLightMapper;

    @Override
    public List<Map> pilotLightStatus() {
        List<Map> list = pilotLightMapper.pilotLightStatus(12);
        JSONObject obj;
        for (Map m : list) {
            if (m.containsKey("statusDetails") && StringUtils.isNotNull(m.get("statusDetails"))) {
                obj = JSON.parseObject(String.valueOf(m.get("statusDetails")));
                m.put("model", obj.getString("model"));
                m.put("currentVisibility", obj.getInteger("currentVisibility") == -1 ? "无" : obj.getString("currentVisibility"));
                m.remove("statusDetails");
            }
        }
        return list;
    }

    @Override
    public List<TblDeviceStatus> getRtStatus(Integer roadId) {
        Example example = new Example(TblDeviceStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("device_type", 12);
        if (roadId != null) {
            criteria.andEqualTo("deviceId", roadId);
        }
        List<TblDeviceStatus> list = tblDeviceStatusMapper.selectByExample(example);
        return list;
    }

    @Override
    public List<Map> visibilityTotal(Date startTime, Date endTime) {
        return pilotLightMapper.visibilityTotal(startTime,endTime);
    }

    @Override
    public JSONArray visibilityTrend(Date startTime, Date endTime) {
        List<Map> listXD = pilotLightMapper.visibilityTrendXD(startTime,endTime);
        List<Map> listDZ = pilotLightMapper.visibilityTrendDZ(startTime,endTime);
        JSONArray result = new JSONArray();
        JSONObject obj;
        for (Map m : listXD) {
            if (m.containsKey("statusDetails") && StringUtils.isNotNull(m.get("statusDetails"))) {
                obj = JSON.parseObject(String.valueOf(m.get("statusDetails")));
                m.put("visibility",obj.getInteger("currentVisibility"));
                m.remove("statusDetails");
            }
        }
        for (Map m : listDZ) {
            if (m.containsKey("statusDetails") && StringUtils.isNotNull(m.get("statusDetails"))) {
                obj = JSON.parseObject(String.valueOf(m.get("statusDetails")));
                m.put("visibility",obj.getInteger("currentVisibility"));
                m.remove("statusDetails");
            }
        }
        result.add(0,listXD);
        result.add(1,listDZ);
        return result;
    }
}
