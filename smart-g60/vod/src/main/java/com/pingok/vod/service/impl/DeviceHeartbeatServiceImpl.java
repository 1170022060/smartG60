package com.pingok.vod.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.vod.domain.TblDeviceInfo;
import com.pingok.vod.service.IDeviceHeartbeatService;
import com.pingok.vod.service.IDeviceInfoService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import com.ruoyi.system.api.domain.device.TblDeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 设备心跳 服务层处理
 *
 * @author qiumin
 */
@Service
public class DeviceHeartbeatServiceImpl implements IDeviceHeartbeatService {

    @Autowired
    private IDeviceInfoService iDeviceInfoService;
    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    @Override
    public void heartbeat(JSONArray heartbeat) {
        TblDeviceInfo tblDeviceInfo;
        TblDeviceStatus tblDeviceStatus;
        JSONObject o;
        for (int i = 0; i < heartbeat.size(); i++) {
            o = heartbeat.getJSONObject(i);
            tblDeviceInfo = iDeviceInfoService.findByDeviceId(o.getString("id"));
            tblDeviceStatus = new TblDeviceStatus();
            tblDeviceStatus.setDeviceId(tblDeviceInfo.getId());
            switch (o.getString("status")) {
                case "ONLINE":
                    tblDeviceStatus.setStatus(1);
                    tblDeviceStatus.setStatusDesc("在线");
                    break;
                case "OFFLINE":
                    tblDeviceStatus.setStatus(0);
                    tblDeviceStatus.setStatusDesc("离线");
                    break;
            }
            tblDeviceStatus.setTime(DateUtils.getNowDate());
            remoteDeviceMonitorService.updateHeartbeat(tblDeviceStatus);
        }
    }
}
