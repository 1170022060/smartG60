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

    public static String HOST;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
    }
}
