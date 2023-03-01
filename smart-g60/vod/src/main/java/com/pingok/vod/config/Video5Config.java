package com.pingok.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lal
 */
@Component
public class Video5Config implements InitializingBean {
    
    @Value("${videoFive.host}")
    private String host;

    @Value("${videoFive.userId}")
    private String userId;

    @Value("${videoFive.ak}")
    private String ak;

    @Value("${videoFive.sk}")
    private String sk;

    public static String HOST;
    public static String USERID;
    public static String AK;
    public static String SK;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        USERID = userId;
        AK = ak;
        SK = sk;
    }
}
