package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class ArtemisLocalConfig implements InitializingBean {
    @Value("${artemisConfig.host}")
    private String host;

    @Value("${artemisConfig.appKey}")
    private String appKey;

    @Value("${artemisConfig.appSecret}")
    private String appSecret;

    public static String HOST;
    public static String APPKEY;
    public static String APPSECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        APPKEY = appKey;
        APPSECRET = appSecret;
    }
}
