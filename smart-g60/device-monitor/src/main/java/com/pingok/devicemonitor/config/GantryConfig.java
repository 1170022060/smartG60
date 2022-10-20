package com.pingok.devicemonitor.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 门架 配置信息
 *
 * @author qiumin
 */
@Component
public class GantryConfig implements InitializingBean {

    /**
     * Gantry
     */
    @Value("${Gantry.host}")
    private String host;


    public static String HOST;

    @Override
    public void afterPropertiesSet() {
        HOST = host;
    }
}
