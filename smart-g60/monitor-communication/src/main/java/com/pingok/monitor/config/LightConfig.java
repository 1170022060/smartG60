package com.pingok.monitor.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 引导灯服务端 配置信息
 *
 * @author qiumin
 */
@Component
public class LightConfig implements InitializingBean {


    /**
     * 引导灯服务端 host
     */
    @Value("${light.host}")
    private String host;


    @Value("${light.username}")
    private String username;

    @Value("${light.password}")
    private String password;

    public static String HOST;

    public static String USERNAME;

    public static String PASSWORD;

    @Override
    public void afterPropertiesSet() {
        HOST = host;
        USERNAME = username;
        PASSWORD = password;
    }
}
