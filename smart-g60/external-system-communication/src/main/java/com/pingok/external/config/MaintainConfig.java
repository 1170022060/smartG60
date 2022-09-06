package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MaintainConfig implements InitializingBean {
    @Value("${maintain.user}")
    private String user;
    @Value("${maintain.password}")
    private String password;

    public static String USER;
    public static String PASSWORD;
    @Override
    public void afterPropertiesSet() throws Exception {

        USER = user;
        PASSWORD = password;
    }
}
