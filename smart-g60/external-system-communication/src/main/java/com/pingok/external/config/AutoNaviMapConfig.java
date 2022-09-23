package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class AutoNaviMapConfig implements InitializingBean {

    @Value("${amap.host}")
    private String host;

    @Value("${amap.adcode}")
    private String adcode;

    @Value("${amap.clientKey}")
    private String clientKey;

    @Value("${amap.callback}")
    private String callback;

    @Value("${amap.sourceId}")
    private String sourceId;

    @Value("${amap.secret}")
    private String secret;

    public static String HOST;
    public static String ADCODE;
    public static String CLIENTKEY;
    public static String CALLBACK;
    public static String SOURCEID;
    public static String SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        ADCODE = adcode;
        CLIENTKEY = clientKey;
        CALLBACK = callback;
        SOURCEID = sourceId;
        SECRET = secret;
    }
}
