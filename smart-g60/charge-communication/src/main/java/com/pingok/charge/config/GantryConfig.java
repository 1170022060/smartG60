package com.pingok.charge.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GantryConfig implements InitializingBean {

    @Value("${gantry.v2x.url}")
    private String gantryV2XUrl;

    public static String V2XUrl;

    @Override
    public void afterPropertiesSet() throws Exception {
        V2XUrl = gantryV2XUrl;
    }
}
