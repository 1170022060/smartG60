package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class DingTalkConfig implements InitializingBean {
    @Value("${dingTalk.redisKey}")
    private String redisKey;

    @Value("${dingTalk.host}")
    private String host;

    @Value("${dingTalk.appKey}")
    private String appKey;

    @Value("${dingTalk.appSecret}")
    private String appSecret;

    @Value("${dingTalk.aesKey}")
    private String aesKey;

    @Value("${dingTalk.aesToken}")
    private String aesToken;

    public static String REDIS_KEY;
    public static String HOST;
    public static String APP_KEY;
    public static String APP_SECRET;
    public static String AES_KEY;
    public static String AES_TOKEN;

    @Override
    public void afterPropertiesSet() throws Exception {
        REDIS_KEY = redisKey;
        HOST = host;
        APP_KEY = appKey;
        APP_SECRET = appSecret;
        AES_KEY = aesKey;
        AES_TOKEN = aesToken;
    }
}
