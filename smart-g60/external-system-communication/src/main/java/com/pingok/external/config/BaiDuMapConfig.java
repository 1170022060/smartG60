package com.pingok.external.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * DingTalk配置
 */
@Component
public class BaiDuMapConfig implements InitializingBean {

    @Value("${baidu.host}")
    private String host;

    @Value("${baidu.ak}")
    private String ak;

    @Value("${baidu.nodeId}")
    private Integer nodeId;

    @Value("${baidu.nodeName}")
    private String nodeName;

    @Value("${baidu.provinceCode}")
    private Integer provinceCode;

    @Value("${baidu.cityCode}")
    private Integer cityCode;

    @Value("${baidu.dataType}")
    private String dataType;

    public static String HOST;
    public static String AK;
    public static Integer NODEID;
    public static String NODENAME;
    public static Integer PROVINCECODE;
    public static Integer CITYCODE;
    public static String DATATYPE;

    @Override
    public void afterPropertiesSet() throws Exception {
        HOST = host;
        AK = ak;
        NODEID = nodeId;
        NODENAME = nodeName;
        PROVINCECODE = provinceCode;
        CITYCODE = cityCode;
        DATATYPE = dataType;
    }
}
