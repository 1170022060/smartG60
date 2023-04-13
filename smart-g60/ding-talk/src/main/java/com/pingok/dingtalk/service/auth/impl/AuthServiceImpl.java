package com.pingok.dingtalk.service.auth.impl;

import com.pingok.dingtalk.config.DingTalkAccountProperties;
import com.pingok.dingtalk.daemon.auth.AuthInfo;
import com.pingok.dingtalk.feign.AuthFeign;
import com.pingok.dingtalk.service.auth.AuthService;
import com.ruoyi.common.core.constant.Constants;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author chenwg
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthFeign authFeign;
    @Resource
    private RedisService redisService;
    private final String DING_TALK_ACCESS_TOKEN_KEY = "ding_talk:access_token_ket";
    @Resource
    private DingTalkAccountProperties talkAccountProperties;


    @Override
    public String getAuthToken() {
        if (redisService.hasKey(DING_TALK_ACCESS_TOKEN_KEY)) {
            return redisService.getCacheObject(DING_TALK_ACCESS_TOKEN_KEY);
        }
        AuthInfo authToken = authFeign.getAuthToken(talkAccountProperties.getTenantId(),
                talkAccountProperties.getAuthorization(),
                talkAccountProperties.getAppKey(),
                talkAccountProperties.getAppSecret());
        String access_token = authToken.getAccess_token();
        redisService.setCacheObject(DING_TALK_ACCESS_TOKEN_KEY, access_token, 50L, TimeUnit.MINUTES);
        return access_token;
    }
}
