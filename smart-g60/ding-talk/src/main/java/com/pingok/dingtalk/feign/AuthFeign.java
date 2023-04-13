package com.pingok.dingtalk.feign;

import com.pingok.dingtalk.daemon.auth.AuthInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author chenwg
 */
@Component
@FeignClient(name = "auth-feign", url = "${ding-talk.base-url}/blade-auth")
public interface AuthFeign {
    /**
     * 获取钉钉authToken
     *
     * @param TenantId
     * @param authorization
     * @param appKey
     * @param appSecret
     * @return
     */
    @GetMapping(value = "/oauth/getAuthToken")
    AuthInfo getAuthToken(@RequestHeader("Tenant-ld") String TenantId,
                          @RequestHeader("Authorization") String authorization,
                          @RequestParam("appKey") String appKey,
                          @RequestParam("appSecret") String appSecret);
}
