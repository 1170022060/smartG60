package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 阿里天气配置
 */
@Component
public class HfWeatherConfig implements InitializingBean {
    @Value("${hfWeather.host}")
    private String host;

    @Value("${hfWeather.locSH}")
    private String locSH;

    @Value("${hfWeather.key}")
    private String key;

    public static String HOST;
    public static String LOCSH;

    public static String KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        LOCSH = locSH;
        KEY = key;
    }
}
