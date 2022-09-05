package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class RoadDoctorConfig implements InitializingBean {

    @Value("${roadDoctor.host}")
    private String host;
    @Value("${roadDoctor.user}")
    private String user;
    @Value("${roadDoctor.password}")
    private String password;

    public static String HOST;
    public static String USER;
    public static String PASSWORD;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        USER = user;
        PASSWORD = password;
    }
}
