package com.pingok.charge.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * daas 配置信息
 *
 * @author qiumin
 */
@Component
public class DaasConfig implements InitializingBean {

    /**
     * dass
     */
    @Value("${daas.host}")
    private String host;


    public static String HOST;

    @Override
    public void afterPropertiesSet() {
        HOST = host;
    }
}
