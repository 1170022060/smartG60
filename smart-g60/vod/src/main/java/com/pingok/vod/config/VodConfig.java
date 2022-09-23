package com.pingok.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * VOD配置
 */
@Component
public class VodConfig implements InitializingBean {

    @Value("${vod.host}")
    private String host;

    @Value("${vod.user}")
    private String user;

    @Value("${vod.pwd}")
    private String pwd;


    public static String HOST;
    public static String USER;
    public static String PWD;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        USER = user;
        PWD = pwd;
    }
}
