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
public class EncryptConfig implements InitializingBean {


    @Value("${encrypt.subject}")
    private String subject;


    @Value("${encrypt.digestAlg}")
    private String digestAlg;



    public static String SUBJECT;
    public static String DIGESTALG;


    @Override
    public void afterPropertiesSet() {
        SUBJECT = subject;
        DIGESTALG = digestAlg;
    }
}
