package com.ruoyi.monitorExternalSystem.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HostConfig implements InitializingBean {

    /**
     * dass
     */
    @Value("${daas.host}")
    private String dassHost;

    public static String DASSHOST;

    @Override
    public void afterPropertiesSet() {
        DASSHOST = dassHost;

    }
}
