package com.ruoyi.monitorExternalSystem.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PrimaryConfig implements InitializingBean {
    /**
     * 两客一危数据服务 host
     */
    @Value("${primary.host}")
    private String host;

    @Value("${primary.token}")
    private String token;

    @Value("${primary.font}")
    private String font;

    public static String HOST;
    public static String TOKEN;
    public static String FONT;

    @Override
    public void afterPropertiesSet() {
        HOST = host;
        TOKEN = token;
        FONT = font;
    }
}
