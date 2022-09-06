package com.pingok.devicemonitor.service.pilotLight.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.device.TblDeviceInfo;
import com.pingok.devicemonitor.domain.device.TblDeviceStatus;
import com.pingok.devicemonitor.domain.device.TblDeviceStatusLog;
import com.pingok.devicemonitor.mapper.device.TblDeviceInfoMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceStatusLogMapper;
import com.pingok.devicemonitor.mapper.device.TblDeviceStatusMapper;
import com.pingok.devicemonitor.service.pilotLight.IPilotLightService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class PilotLightServiceImpl implements IPilotLightService {

    private String host = "http://10.31.42.210:8088";
    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;
    @Autowired
    private TblDeviceStatusMapper tblDeviceStatusMapper;
    @Autowired
    private TblDeviceStatusLogMapper tblDeviceStatusLogMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private RedisService redisService;

    @Override
    public void collectRtStatus() {
        String token = getToken();
        if(!StringUtils.isEmpty(token)) {
            List<TblDeviceInfo> devList = tblDeviceInfoMapper.findByDeviceType("12");
            Map<String, Object> params = new HashMap<>();
            params.put("token", token);
            for (TblDeviceInfo dev : devList) {
                params.put("roadId", dev.getDeviceId());
                String resp = HttpUtil.get(host + "/fogArea/sys/get", params);
                if(!StringUtils.isEmpty(resp)) {
                    JSONObject jo = JSON.parseObject(resp);
                    if(200 == jo.getInteger("status")) {
                        String data = JSON.toJSONString(jo.get("data"));
                        TblDeviceStatus rt = tblDeviceStatusMapper.findLightRtStatus(dev.getDeviceId());
                        if(rt != null) {
                            rt.setStatusDetails(data);
                        }
                        TblDeviceStatusLog rec = new TblDeviceStatusLog();
                        rec.setId(remoteIdProducerService.nextId());
                        rec.setStatusDetails(data);
                        tblDeviceStatusLogMapper.insert(rec);
                    }
                }
            }

        }
    }

    private String getToken() {
        String host = "http://10.31.42.210:8088";
        String token = redisService.getCacheObject("light_token");
        if(StringUtils.isEmpty(token)) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", "上海匝道系统");
            params.put("password", "shanghai123");
            params.put("system", 1);
            String resp = HttpUtil.post(host + "/user/login", params);
            if(!resp.isEmpty()) {
                JSONObject ret = JSON.parseObject(resp);
                token = ret.getJSONObject("data").getString("token");
                redisService.setCacheObject("light_token", token, 71l, TimeUnit.HOURS);
            } else {
                log.error("引导灯-登录失败！");
            }
        }
        return token;
    }
}
