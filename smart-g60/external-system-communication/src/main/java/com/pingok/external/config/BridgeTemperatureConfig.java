package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class BridgeTemperatureConfig implements InitializingBean {
    @Value("${bridgeTemperature.host}")
    private String host;

    @Value("${bridgeTemperature.sessid}")
    private String sessid;

    public static String HOST;
    public static String SESSID;
    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        SESSID = sessid;
    }
}
