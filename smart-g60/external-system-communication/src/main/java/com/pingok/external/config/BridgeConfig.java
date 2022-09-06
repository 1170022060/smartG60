package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class BridgeConfig implements InitializingBean {
    @Value("${bridge.host}")
    private String host;

    @Value("${bridge.token}")
    private String token;

    public static String HOST;
    public static String TOKEN;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        TOKEN = token;
    }
}
