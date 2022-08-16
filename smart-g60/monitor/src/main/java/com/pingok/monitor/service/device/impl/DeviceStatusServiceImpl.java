package com.pingok.monitor.service.device.impl;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.pingok.monitor.domain.device.vo.DeviceInfoVo;
import com.pingok.monitor.mapper.device.TblBaseStationInfoMapper;
import com.pingok.monitor.mapper.device.TblBridgeInfoMapper;
import com.pingok.monitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.monitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.monitor.service.device.IDeviceStatusService;
import com.ruoyi.common.core.utils.DateUtils;
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
    @Autowired
    private TblBaseStationInfoMapper tblBaseStationInfoMapper;
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private TblBridgeInfoMapper tblBridgeInfoMapper;


    @Override
    public void checkStatus() {
        List<TblDeviceStatus> list = tblDeviceStatusMapper.selectAll();
        for (TblDeviceStatus s : list) {
            if(Math.abs(DateUtils.getDatePoorMin(s.getTime(),DateUtils.getNowDate()))>=6){
                s.setStatus(0);
                s.setStatusDesc("设备离线");
                tblDeviceStatusMapper.updateByPrimaryKey(s);
            }
        }
    }

    @Override
    public List<Map> list(Long deviceCategory, String deviceName, String deviceId) {
        List<Map> maps = tblDeviceStatusMapper.list(deviceCategory, deviceName, deviceId);
        for (Map m : maps) {
            if (m.get("statusDetails") != null) {
                m.put("statusDetails", JSONObject.parseObject(String.valueOf(m.get("statusDetails"))));
            }
        }
        return maps;
    }

    @Override
    public List<Map> selectBaseStation() {
        return tblBaseStationInfoMapper.selectBaseStation();
    }

    @Override
    public List<Map> selectBridgeInfo() {
        return tblBridgeInfoMapper.selectBridgeInfo();
    }

    @Override
    public List<DeviceInfoVo> selectVMS() {
        return tblDeviceInfoMapper.selectVMS();
    }

    @Override
    public List<DeviceInfoVo> selectVD() {
        return tblDeviceInfoMapper.selectVD();
    }

    @Override
    public List<DeviceInfoVo> selectCAM() {
        return tblDeviceInfoMapper.selectCAM();
    }

    @Override
    public List<DeviceInfoVo> selectPilotLight() {
        return tblDeviceInfoMapper.selectPilotLight();
    }
}
