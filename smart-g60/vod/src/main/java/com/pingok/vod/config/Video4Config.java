package com.pingok.vod.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lal
 */
@Component
public class Video4Config implements InitializingBean {

    @Value("${videoFour.host}")
    private String host;

    @Value("${videoFour.userId}")
    private String userId;

    @Value("${videoFour.ak}")
    private String ak;

    @Value("${videoFour.sk}")
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
