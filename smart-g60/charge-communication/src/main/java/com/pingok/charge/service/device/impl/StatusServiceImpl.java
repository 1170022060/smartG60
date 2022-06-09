package com.pingok.charge.service.device.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.device.TblDeviceInfo;
import com.pingok.charge.domain.device.TblDeviceStatus;
import com.pingok.charge.service.device.IStatusService;
import com.pingok.charge.service.heartbeat.IHeartbeatService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class StatusServiceImpl implements IStatusService {

    @Value("${daas.host}")
    private String host;

    @Autowired
    private IHeartbeatService iHeartbeatService;
    @Autowired
    private RemoteConfigService remoteConfigService;

    @Override
    public void serverStatus(JSONArray array) {
        BigDecimal threshold;
        StringBuffer statusDesc = new StringBuffer();
        R<String> r;
        boolean online;
        JSONObject jsonObject;
        TblDeviceInfo deviceInfo;
        TblDeviceStatus deviceStatus;
        String post;
        R ret;
        for (int i = 0; i < array.size(); i++) {
            deviceInfo = JSON.parseObject(array.getString(i), TblDeviceInfo.class);
            deviceStatus = new TblDeviceStatus();
            deviceStatus.setDeviceId(deviceInfo.getId());
            deviceStatus.setTime(DateUtils.getNowDate());
            deviceStatus.setStatus(1);
            deviceStatus.setStatusDesc("正常");
            online = iHeartbeatService.ping(deviceInfo.getDeviceIp());
            if (online) {
                jsonObject = iHeartbeatService.serverSnmp(deviceInfo.getDeviceIp());
                r = remoteConfigService.getConfigKey("cup.threshold");
                if (R.SUCCESS == r.getCode()) {
                    threshold = new BigDecimal(r.getData());
                    if (jsonObject.getBigDecimal("cpu").compareTo(threshold) > -1) {
                        deviceStatus.setStatus(0);
                        statusDesc.append("|CPU占用率过高|");
                    }
                }
                r = remoteConfigService.getConfigKey("memory.threshold");
                if (R.SUCCESS == r.getCode()) {
                    threshold = new BigDecimal(r.getData());
                    if (jsonObject.getBigDecimal("memoryUsage").compareTo(threshold) > -1) {
                        deviceStatus.setStatus(0);
                        statusDesc.append("|内存占用率过高|");
                        jsonObject.put("memory", 0);
                    } else {
                        jsonObject.put("memory", 1);
                    }
                }
                r = remoteConfigService.getConfigKey("disk.threshold");
                if (R.SUCCESS == r.getCode()) {
                    threshold = new BigDecimal(r.getData());
                    if (jsonObject.getBigDecimal("storageUsage").compareTo(threshold) > -1) {
                        deviceStatus.setStatus(0);
                        statusDesc.append("|硬盘占用率过高|");
                        jsonObject.put("storage", 0);

                    } else {
                        jsonObject.put("storage", 1);
                    }
                }
                if (statusDesc != null) {
                    deviceStatus.setStatusDesc(statusDesc.toString());
                }
                deviceStatus.setStatusDetails(jsonObject.toJSONString());
            } else {
                deviceStatus.setStatus(0);
                deviceStatus.setStatusDesc("离线");
            }
            post = HttpUtil.post(host + "/device-monitor/deviceMonitor", JSON.toJSONString(deviceStatus));
            if (!StringUtils.isEmpty(post)) {
                ret = JSON.parseObject(post, R.class);
                if (R.FAIL == ret.getCode()) {
                    log.error(deviceInfo.getDeviceId() + "设备状态上报失败：" + ret.getMsg());
                }
            } else {
                log.error(deviceInfo.getDeviceId() + "设备状态上报状态未知");
            }
        }
    }

    @Override
    public void pingStatus(JSONArray array) {
        StringBuffer statusDesc = new StringBuffer();
        R<String> r;
        boolean online;
        JSONObject jsonObject;
        TblDeviceInfo deviceInfo;
        TblDeviceStatus deviceStatus;
        String post;
        R ret;
        for (int i = 0; i < array.size(); i++) {
            deviceInfo = JSON.parseObject(array.getString(i), TblDeviceInfo.class);
            deviceStatus = new TblDeviceStatus();
            deviceStatus.setDeviceId(deviceInfo.getId());
            deviceStatus.setTime(DateUtils.getNowDate());
            deviceStatus.setStatus(1);
            deviceStatus.setStatusDesc("正常");
            online = iHeartbeatService.ping(deviceInfo.getDeviceIp());
            if (!online) {
                deviceStatus.setStatus(0);
                deviceStatus.setStatusDesc("离线");
            }
            post = HttpUtil.post(host + "/device-monitor/deviceMonitor", deviceStatus.toString());
            if (!StringUtils.isEmpty(post)) {
                ret = JSON.parseObject(post, R.class);
                if (R.FAIL == ret.getCode()) {
                    log.error(deviceInfo.getDeviceId() + "设备状态上报失败：" + ret.getMsg());
                }
            } else {
                log.error(deviceInfo.getDeviceId() + "设备状态上报状态未知");
            }
        }
    }

    @Override
    public void switchStatus(JSONArray array) {
        serverStatus(array);
    }

    @Override
    public void firewallStatus(JSONArray array) {
        serverStatus(array);
    }
}
