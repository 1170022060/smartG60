package com.pingok.dingtalk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;
import java.util.List;

/**
 * @author chenwg
 */
@Data
@ConfigurationProperties(prefix = "ding-talk")
@Configuration
public class DingTalkAccountProperties implements Serializable {
    private String tenantId;
    private String authorization;
    private String appKey;
    private String appSecret;
    private List<String> whiteList;
}
