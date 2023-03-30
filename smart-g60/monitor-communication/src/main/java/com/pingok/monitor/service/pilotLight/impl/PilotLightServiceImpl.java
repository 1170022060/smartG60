package com.pingok.monitor.service.pilotLight.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.config.LightConfig;
import com.pingok.monitor.domain.device.TblDeviceStatus;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author
 * @time 2022/5/12 9:56
 */
@Slf4j
@Service
public class PilotLightServiceImpl implements IPilotLightService {


    @Autowired
    private RedisService redisService;

    private static final int[][] paramValues = {
            {8, 3, 0, 0, 0, 5, 0, 12, 7},
            {2, 3, 0, 0, 0, 5, 0, 12, 7},
            {8, 3, 8, 1, 3, 5, 1, 12, 7},
            {2, 3, 1, 1, 3, 5, 1, 12, 7},
            {0, 0, 9, 4, 0, 5, 0, 12, 7},
            {6, 1, 0, 0, 0, 5, 0, 12, 7},
            {8, 4, 8, 4, 3, 5, 1, 12, 7},
            {0, 0, 0, 0, 0, 5, 0, 0, 7}
    };

    @Override
    public void commandSend(JSONObject body) {
        String deviceId = body.getString("deviceId");
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("token", getToken());
        paramMap.put("command", getParamV2(body.getString("cmdType"), deviceId).toJSONString());
        String resp = HttpUtil.post(LightConfig.HOST + "/command/device/send", paramMap);
        if (!StringUtils.isEmpty(resp)) {
            JSONObject obj = JSON.parseObject(resp);
            if (obj.getInteger("status") != 200) {
                log.error("超视距诱导等：发送指令失败，消息体内容——————————" + body.toJSONString());
            }
        }
    }

    @Override
    public void updateStatus() {
        JSONArray roads = getDeviceIds();
        JSONObject road;
        JSONArray deviceIds;
        Map<String, Object> params;
        String resp;
        JSONObject obj;
        TblDeviceStatus deviceStatus;
        JSONObject statusDetails;
        Long deviceId = null;
        R ret;
        for (int i = 0; i < roads.size(); i++) {
            road = roads.getJSONObject(i);
            params = new HashMap<>();
            params.put("token", getToken());
            params.put("roadId", road.getString("roadId"));
            deviceIds = road.getJSONArray("deviceIds");
            resp = HttpUtil.get(LightConfig.HOST + "/fogArea/sys/get", params);
            if (!StringUtils.isEmpty(resp)) {
                obj = JSONObject.parseObject(resp);
                if (obj.getInteger("status") == 200) {
                    for (int j = 0; j < deviceIds.size(); j++) {
                        deviceId = deviceIds.getLong(j);
                        deviceStatus = new TblDeviceStatus();
                        deviceStatus.setDeviceId(deviceId);
                        deviceStatus.setTime(DateUtils.getNowDate());
                        statusDetails = new JSONObject();
                        switch (obj.getJSONObject("data").getString("model")){
                            case "诱导模式":
                                statusDetails.put("model", "行车诱导模式");
                                break;
                            case "预警模式":
                                statusDetails.put("model", "防追尾预警模式");
                                break;
                            default:
                                statusDetails.put("model", obj.getJSONObject("data").getString("model"));
                                break;
                        }
                        statusDetails.put("currentVisibility", obj.getJSONObject("data").getString("currentVisibility"));
//                        deviceStatus.setStatus(1);
//                        deviceStatus.setStatusDesc("正常");
                        deviceStatus.setStatusDetails(statusDetails.toJSONString());
                        resp = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/deviceMonitor", JSON.toJSONString(deviceStatus));
                        if (!StringUtils.isEmpty(resp)) {
                            ret = JSON.parseObject(resp, R.class);
                            if (R.FAIL == ret.getCode()) {
                                log.error(deviceId + "设备状态上报失败：" + ret.getMsg());
                            }
                        } else {
                            log.error(deviceId + "设备状态上报状态未知");
                        }
                    }
                }
                else {
                    log.error("获取超视距诱导系统状态失败："+obj.getInteger("msg"));
                    for (int j = 0; j < deviceIds.size(); j++) {
                        deviceId = deviceIds.getLong(j);
                        deviceStatus = new TblDeviceStatus();
                        deviceStatus.setDeviceId(deviceId);
                        deviceStatus.setTime(DateUtils.getNowDate());
                        deviceStatus.setStatus(0);
                        deviceStatus.setStatusDesc(obj.getString("msg"));
                        deviceStatus.setFaultType("offLine");
                        resp = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/deviceMonitor", JSON.toJSONString(deviceStatus));
                        if (!StringUtils.isEmpty(resp)) {
                            ret = JSON.parseObject(resp, R.class);
                            if (R.FAIL == ret.getCode()) {
                                log.error(deviceId + "设备状态上报失败：" + ret.getMsg());
                            }
                        } else {
                            log.error(deviceId + "设备状态上报状态未知");
                        }
                    }
                }
            }
            else {
                log.error("获取超视距诱导系统状态失败：对方服务无响应");
                for (int j = 0; j < deviceIds.size(); j++) {
                    deviceId = deviceIds.getLong(j);
                    deviceStatus = new TblDeviceStatus();
                    deviceStatus.setDeviceId(deviceId);
                    deviceStatus.setTime(DateUtils.getNowDate());
                    deviceStatus.setStatus(0);
                    deviceStatus.setStatusDesc("服务无响应");
                    deviceStatus.setFaultType("offLine");
                    resp = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/deviceMonitor", JSON.toJSONString(deviceStatus));
                    if (!StringUtils.isEmpty(resp)) {
                        ret = JSON.parseObject(resp, R.class);
                        if (R.FAIL == ret.getCode()) {
                            log.error(deviceId + "设备状态上报失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(deviceId + "设备状态上报状态未知");
                    }
                }
            }
        }


    }

    private JSONArray getDeviceIds() {
        JSONObject roadId;
        JSONArray roadIds = new JSONArray();
        JSONArray deviceIds;
        Map<String, Object> params = new HashMap<>();
        params.put("token", getToken());
        String resp = HttpUtil.get(LightConfig.HOST + "/sys/roadList/deviceList", params);
        if (!StringUtils.isEmpty(resp)) {
            JSONObject obj = JSONObject.parseObject(resp);
            if (obj.getInteger("status") == 200) {
                JSONArray rows = obj.getJSONArray("rows");
                JSONArray devs;
                for (int i = 0; i < rows.size(); i++) {
                    roadId = new JSONObject();
                    roadId.put("roadId", rows.getJSONObject(i).getString("roadId"));

                    devs = rows.getJSONObject(i).getJSONArray("deviceList");
                    deviceIds = new JSONArray();
                    for (int j = 0; j < devs.size(); j++) {
                        deviceIds.add(devs.getJSONObject(j).getInteger("deviceId"));
                    }
                    roadId.put("deviceIds", deviceIds);
                    roadIds.add(roadId);
                }
            }
        }
        return roadIds;
    }


    private String getToken() {
        String token = null;
        if (!redisService.hasKey("lightToken")) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", LightConfig.USERNAME);
            params.put("password", LightConfig.PASSWORD);
            params.put("system", 1);
            String resp = HttpUtil.post(LightConfig.HOST + "/user/login", params);
            if (!resp.isEmpty()) {
                JSONObject ret = JSON.parseObject(resp);
                token = ret.getJSONObject("data").getString("token");
                redisService.setCacheObject("lightToken", token, 70l, TimeUnit.HOURS);
            } else {
                log.error("引导灯-登录失败！");
            }
        } else {
            token = redisService.getCacheObject("lightToken");
        }
        return token;
    }


    // 获取发送命令的param（V2）
    JSONObject getParamV2(String value, String deviceId) {
        JSONObject ret = new JSONObject();
        JSONArray ja = new JSONArray();
        Integer index = Integer.parseInt(value);
        if (index < 0 || index > 7) return null;
        int[] values = paramValues[index];

        ret.put("cmdId", 11);
        ret.put("deviceId", deviceId);
        JSONObject jo1 = new JSONObject();
        jo1.put("key", 1);
        jo1.put("value", values[0]);
        ja.add(jo1);
        JSONObject jo2 = new JSONObject();
        jo2.put("key", 2);
        jo2.put("value", values[1]);
        ja.add(jo2);
        JSONObject jo3 = new JSONObject();
        jo3.put("key", 3);
        jo3.put("value", values[2]);
        ja.add(jo3);
        JSONObject jo4 = new JSONObject();
        jo4.put("key", 4);
        jo4.put("value", values[3]);
        ja.add(jo4);
        JSONObject jo5 = new JSONObject();
        jo5.put("key", 5);
        jo5.put("value", values[4]);
        ja.add(jo5);
        JSONObject jo6 = new JSONObject();
        jo6.put("key", 6);
        jo6.put("value", values[5]);
        ja.add(jo6);
        JSONObject jo7 = new JSONObject();
        jo7.put("key", 7);
        jo7.put("value", values[6]);
        ja.add(jo7);
        JSONObject jo8 = new JSONObject();
        jo8.put("key", 8);
        jo8.put("value", values[7]);
        ja.add(jo8);
        JSONObject jo9 = new JSONObject();
        jo9.put("key", 9);
        jo9.put("value", values[8]);
        ja.add(jo9);

        ret.put("param", ja);
        return ret;
    }


}
