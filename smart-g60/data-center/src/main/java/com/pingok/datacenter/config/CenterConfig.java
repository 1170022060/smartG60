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
public class CenterConfig implements InitializingBean {


    @Value("${center.host}")
    private String host;


    @Value("${center.stationGB}")
    private String stationGB;


    @Value("${center.cardPath}")
    private String cardPath;

    @Value("${center.ratePath}")
    private String ratePath;

    @Value("${center.epidemicPath}")
    private String epidemicPath;

    @Value("${center.prefixPath}")
    private String prefixPath;

    @Value("${center.recoveryPath}")
    private String recoveryPath;

    @Value("${center.rescuePath}")
    private String rescuePath;

    @Value("${center.greenPath}")
    private String greenPath;

    public static String HOST;
    public static String STATIONGB;
    public static String CARDPATH;
    public static String RATEPATH;
    public static String EPIDEMICPATH;
    public static String PREFIXPATH;
    public static String RECOVERYPATH;
    public static String RESCUEPATH;
    public static String GREENPATH;

    @Override
    public void afterPropertiesSet() {
        HOST = host;
        STATIONGB = stationGB;
        CARDPATH = cardPath;
        RATEPATH = ratePath;
        EPIDEMICPATH = epidemicPath;
        PREFIXPATH = prefixPath;
        RECOVERYPATH = recoveryPath;
        RESCUEPATH = rescuePath;
        GREENPATH = greenPath;
    }
}
