package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里天气配置
 */
@Component
public class AlWeatherConfig implements InitializingBean {
    @Value("${alWeather.host}")
    private String host;

    @Value("${alWeather.appCode}")
    private String appCode;

    public static String HOST;
    public static String APPCODE;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        APPCODE = appCode;
    }
}
