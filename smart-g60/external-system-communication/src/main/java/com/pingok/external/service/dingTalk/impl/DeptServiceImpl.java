package com.pingok.external.service.dingTalk.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.config.DingTalkConfig;
import com.pingok.external.service.dingTalk.IDeptService;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.pingok.external.util.DingCallbackCrypto;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DeptServiceImpl implements IDeptService {

    @Autowired
    private RedisService redisService;

    @Autowired
    private IDingTalkService iDingTalkService;


    @Override
    public String gettoken() {
        if (!redisService.hasKey(DingTalkConfig.REDIS_KEY)) {
            Map<String, Object> param = new HashMap<>();
            param.put("appkey", DingTalkConfig.APP_KEY);
            param.put("appsecret", DingTalkConfig.APP_SECRET);
            String r = HttpUtil.get(DingTalkConfig.HOST + "/gettoken", param);
            if (!StringUtils.isEmpty(r)) {
                JSONObject object = JSONObject.parseObject(r);
                if (object.getInteger("errcode") == 0) {
                    redisService.setCacheObject(DingTalkConfig.REDIS_KEY, object.getString("access_token"), object.getLong("expires_in"), TimeUnit.SECONDS);
                } else {
                    throw new SecurityException("获取DingTalkToken失败：" + object.getString("errmsg"));
                }
            }
        }
        String token = redisService.getCacheObject(DingTalkConfig.REDIS_KEY);
        return token;
    }


    @Override
    public Map<String, String> callBack(String msg_signature, String timeStamp, String nonce, JSONObject json) {
        try {
            // 1. 从http请求中获取加解密参数
            // 2. 使用加解密类型
            // Constant.OWNER_KEY 说明：
            // 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
            // 2、调用订阅事件接口订阅的事件为企业级事件推送，
            //      此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(DingTalkConfig.AES_TOKEN, DingTalkConfig.AES_KEY, DingTalkConfig.APP_KEY);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);

            // 3. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            String eventType = eventJson.getString("EventType");
            switch (eventType) {
                case "org_dept_create":
                    iDingTalkService.orgDeptCreate(eventJson.getJSONArray("DeptId"));
                    break;
                case "org_dept_modify":
                    iDingTalkService.orgDeptModify(eventJson.getJSONArray("DeptId"));
                    break;
                case "org_dept_remove":
                    iDingTalkService.orgDeptRemove(eventJson.getJSONArray("DeptId"));
                    break;
            }
            // 5. 返回success的加密数据
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
            return successMap;
        } catch (DingCallbackCrypto.DingTalkEncryptException e) {
            throw new RuntimeException(e);
        }
    }
}
