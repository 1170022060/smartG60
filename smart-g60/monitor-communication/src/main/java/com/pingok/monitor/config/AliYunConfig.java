package com.pingok.monitor.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * AliYunSms 配置信息
 *
 * @author qiumin
 */
@Component
public class AliYunConfig implements InitializingBean {

    /**
     * 密钥id
     */
    @Value("${daas.host}")
    private String dassHost;

    /**
     * 密钥
     */
    @Value("${video.host}")
    private String videoHost;

    public static String DASSHOST;
    public static String VIDEOHOST;

    @Override
    public void afterPropertiesSet() {
        DASSHOST = dassHost;
        VIDEOHOST = videoHost;
    }
}
