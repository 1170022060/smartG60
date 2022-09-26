package com.pingok.monitor.service.pilotLight.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.system.api.domain.SysDictData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

//    @Value("${light.host}")
//    private String host;
//    private String host = "localhost:6060/api";

    @Autowired
    private RedisService redisService;

    private static final int[][] paramValues = {
            {8,3,0,0,0,5,0,0,7},
            {2,3,0,0,0,5,0,0,7},
            {8,3,8,1,3,5,1,3,7},
            {2,3,1,1,3,5,1,3,7},
            {0,0,9,4,0,5,0,0,7},
            {6,1,0,0,0,5,0,0,7},
            {8,4,8,4,3,5,1,3,7},
            {0,0,0,0,0,5,0,0,7}
    };

    private String getToken() {
        String token = redisService.getCacheObject("light_token");
        if(StringUtils.isEmpty(token)) {
            Map<String, Object> params = new HashMap<>();
            params.put("username", "上海匝道系统");
            params.put("password", "shanghai123");
            params.put("system", 1);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/user/login", params);
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

    private JSONObject login(Integer system) {
        JSONObject body = new JSONObject();
        body.put("username", "admin");
        body.put("password", "admin");
        body.put("system", system);
        String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/user/login", JSON.toJSONString(body));
        if(!StringUtils.isEmpty(resp)) {
            JSONObject ret = JSON.parseObject(resp);
            return ret;
        }
        return null;
    }

    private JSONObject logout(Object token) {
        JSONObject body = new JSONObject();
        body.put("token", token);
        String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/user/logout", JSON.toJSONString(body));
        if(!StringUtils.isEmpty(resp)) {
            JSONObject ret = JSON.parseObject(resp);
            return ret;
        }
        return null;
    }

    @Override
    public boolean passwordV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/user/password", JSON.toJSONString(body));
            logout(token);
            if(!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean passwordV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/sys/user/update/password", JSON.toJSONString(body));
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getUserInfo() {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("token", token);
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/user", params);
            logout(token);
            if(!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean updateUserInfoV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/user", JSON.toJSONString(body));
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean updateUserInfoV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/sys/user/update", JSON.toJSONString(body));
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRoadInfoV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/road", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRoadInfoV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/road/v2", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRoadInfoById(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/road/" + body.get("roadId"), params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
                return ret;
            }
        }
        return ret;
    }

    @Override
    public boolean getRoadDeviceInfoV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/roadList/deviceList", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRoadDeviceInfoV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/sys/roadList/deviceList", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getOneRoadDeviceInfoV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/road/" + body.get("roadId") + "/device", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getOneRoadDeviceInfoV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("roadId", body.get("roadId"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/road/device/show/list", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getDeviceInfo(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/device/list", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean sendCmdToDeviceV1(JSONObject body) {
        boolean ret = false;
        List<SysDictData> types = DictUtils.getDictCache("pilotlight_paramtype");
        SysDictData find = types.stream()
                            .filter(t -> t.getDictLabel() == body.get("param"))
                            .findFirst().orElse(null);
        if(find != null) {
            JSONObject login = login(3);
            if(200 == login.getInteger("status")) {
                Object token = login.get("token");
                JSONObject send = new JSONObject();
                send.put("token", token);
                send.put("cmd", "SetAGSParam");
                send.put("param", getParamV1(find.getDictValue()));
                String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/device/command/" + body.get("deviceId"), JSON.toJSONString(send));
                logout(token);
                if (!StringUtils.isEmpty(resp)) {
                    JSONObject jo = JSON.parseObject(resp);
                    if(200 == jo.getInteger("status")) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public boolean sendCmdToDeviceV2(JSONObject body) {
        boolean ret = false;
        List<SysDictData> types = DictUtils.getDictCache("light_cmdType");
        SysDictData find = types.stream()
                .filter(t -> t.getDictValue().equals(body.getString("cmdType")))
                .findFirst().orElse(null);
        if(find != null) {
//            JSONObject login = login(3);
            String token = getToken();
            if(!StringUtils.isEmpty(token)) {
                String deviceId = body.getString("deviceId");
                Map<String, Object> paramMap = new HashMap<>();
                paramMap.put("token", token);
                paramMap.put("command",getParamV2(find.getDictValue(), deviceId).toJSONString());
//                send.put("detail", null);
                String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/command/device/send", paramMap);
                if (!StringUtils.isEmpty(resp)) {
                    JSONObject jo = JSON.parseObject(resp);
                    if (200 == jo.getInteger("status")) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRtStatusV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Object deviceId = body.get("deviceId");
            Object localId = body.get("localId");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("deviceId", deviceId);
            params.put("localId", localId);
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/deice/data/dynamic/" + deviceId + "/" + localId, params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRtStatusV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("roadId", body.get("roadId"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/fogArea/sys/get", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRecStatusV1(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("deviceId", body.get("deviceId"));
            params.put("localId", body.get("localId"));
            params.put("specification", body.get("specification"));
            params.put("page", body.get("page"));
            params.put("pageSize", body.get("pageSize"));
            params.put("startTime", body.get("startTime"));
            params.put("endTime", body.get("endTime"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/fogArea/sys/get", params);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getRecStatusV2(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("deviceId", body.get("deviceId"));
            params.put("day", body.get("day"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/edgeClusterStateDat", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean setVisibility(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("deviceId", body.get("deviceId"));
            params.put("visibility", body.get("visibility"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/system/ext/vis", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getFirmware() {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/firmware", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean delFirmware(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("id", body.get("id"));
            String resp = HttpRequest.delete(HostConfig.LIGHTHOST + "/pilotLight/firmware").form(params).execute().body();
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getCode() {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            String resp = HttpRequest.delete(HostConfig.LIGHTHOST + "/pilotLight/state/verificationCode").form(params).execute().body();
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean updateFirmware(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpRequest.put(HostConfig.LIGHTHOST + "/pilotLight/firmware")
                    .body(JSON.toJSONString(body)).execute().body();
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean uploadFile(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            MultipartFile file = (MultipartFile) body.get("uploadFile");
            if (!file.isEmpty()) {
                JSONObject jo = new JSONObject();
                jo.put("token", token);
                jo.put("uploadFile", file);
                String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/uploadFile", JSON.toJSONString(jo));
                logout(token);
                if (!StringUtils.isEmpty(resp)) {
                    JSONObject joRet = JSON.parseObject(resp);
                    if(200 == joRet.getInteger("status")) {
                        ret = true;
                    }
                }
            }
        }
        return ret;
    }

    @Override
    public boolean getFirmwareDevice(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("deviceId", body.get("deviceId"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/firmware/upgrade/device", params);
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean firmwareUpgrade(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            body.put("token", token);
            String resp = HttpUtil.post(HostConfig.LIGHTHOST + "/pilotLight/firmware/upgrade", JSON.toJSONString(body));
            logout(token);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    @Override
    public boolean firmwareVerByArea(JSONObject body) {
        boolean ret = false;
        JSONObject login = login(3);
        if(200 == login.getInteger("status")) {
            Object token = login.get("token");
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("token", token);
            params.put("provice", body.get("provice"));
            params.put("city", body.get("city"));
            params.put("county", body.get("county"));
            String resp = HttpUtil.get(HostConfig.LIGHTHOST + "/pilotLight/firmware/area", params);
            if (!StringUtils.isEmpty(resp)) {
                JSONObject jo = JSON.parseObject(resp);
                if(200 == jo.getInteger("status")) {
                    ret = true;
                }
            }
        }
        return ret;
    }

    // 获取发送命令的param（V1）
    JSONObject getParamV1(String value) {
        JSONObject ret = new JSONObject();
        Integer index = Integer.parseInt(value);
        if(index < 0 || index > 7) return null;
        int[] values = paramValues[index];
        ret.put("yellowLedBrightness", values[0]);
        ret.put("yellowLedFrequency", values[1]);
        ret.put("redLedBrightness", values[2]);
        ret.put("redLedFrequency", values[3]);
        ret.put("tailLength", values[4]);
        ret.put("tailTime", values[5]);
        ret.put("vehicleDetectSwitch", values[6]);
        return ret;
    }
    // 获取发送命令的param（V2）
    JSONObject getParamV2(String value, String deviceId) {
        JSONObject ret = new JSONObject();
        JSONArray ja = new JSONArray();
        Integer index = Integer.parseInt(value);
        if(index < 0 || index > 7) return null;
        int[] values = paramValues[index];

        ret.put("cmdId", 11);
        ret.put("deviceId", deviceId);
        JSONObject jo1 = new JSONObject(); jo1.put("key",1); jo1.put("value", values[0]); ja.add(jo1);
        JSONObject jo2 = new JSONObject(); jo2.put("key",2); jo2.put("value", values[1]); ja.add(jo2);
        JSONObject jo3 = new JSONObject(); jo3.put("key",3); jo3.put("value", values[2]); ja.add(jo3);
        JSONObject jo4 = new JSONObject(); jo4.put("key",4); jo4.put("value", values[3]); ja.add(jo4);
        JSONObject jo5 = new JSONObject(); jo5.put("key",5); jo5.put("value", values[4]); ja.add(jo5);
        JSONObject jo6 = new JSONObject(); jo6.put("key",6); jo6.put("value", values[5]); ja.add(jo6);
        JSONObject jo7 = new JSONObject(); jo7.put("key",7); jo7.put("value", values[6]); ja.add(jo7);
        JSONObject jo8 = new JSONObject(); jo8.put("key",8); jo8.put("value", values[7]); ja.add(jo8);
        JSONObject jo9 = new JSONObject(); jo9.put("key",9); jo9.put("value", values[8]); ja.add(jo9);

        ret.put("param", ja);
        return ret;
    }
}
