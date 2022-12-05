package com.pingok.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lal
 */
@Component
public class Video6Config implements InitializingBean {

    @Value("${videoSix.host}")
    private String host;

    @Value("${videoSix.userId}")
    private String userId;

    @Value("${videoSix.ak}")
    private String ak;

    @Value("${videoSix.sk}")
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
