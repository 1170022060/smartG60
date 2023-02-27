package com.pingok.datacenter.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Host 配置信息
 *
 * @author qiumin
 */
@Component
public class ProvincialCentersConfig implements InitializingBean {


    @Value("${provincialCenters.host}")
    private String host;


    @Value("${provincialCenters.ticket}")
    private String ticket;



    public static String HOST;
    public static String TICKET;


    @Override
    public void afterPropertiesSet() {
        HOST = host;
        TICKET = ticket;
    }
}
