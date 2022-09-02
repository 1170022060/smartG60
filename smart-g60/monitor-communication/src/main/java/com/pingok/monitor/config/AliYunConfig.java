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
     * dass
     */
    @Value("${daas.host}")
    private String dassHost;

    /**
     * 事件识别
     */
    @Value("${video.host}")
    private String videoHost;

    /**
     * 引导灯服务端 host
     */
    @Value("${light.host}")
    private String lightHost;

    public static String DASSHOST;
    public static String VIDEOHOST;
    public static String LIGHTHOST;

    @Override
    public void afterPropertiesSet() {
        DASSHOST = dassHost;
        VIDEOHOST = videoHost;
        LIGHTHOST = lightHost;
    }
}
