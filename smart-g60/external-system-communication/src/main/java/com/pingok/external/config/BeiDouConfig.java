package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class BeiDouConfig implements InitializingBean {
    @Value("${beiDou.host}")
    private String host;

    @Value("${beiDou.username}")
    private String username;

    @Value("${beiDou.password}")
    private String password;

    public static String HOST;
    public static String USERNAME;
    public static String PASSWORD;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        USERNAME = username;
        PASSWORD = password;
    }
}
