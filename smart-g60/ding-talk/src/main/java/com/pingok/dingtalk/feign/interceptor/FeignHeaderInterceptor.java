package com.pingok.dingtalk.feign.interceptor;

import com.pingok.dingtalk.config.DingTalkAccountProperties;
import com.pingok.dingtalk.service.auth.AuthService;
import com.ruoyi.common.core.utils.StringUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 添加token到请求头
 *
 * @author chenwg
 */
@Configuration
public class FeignHeaderInterceptor implements RequestInterceptor {

    @Resource
    private DingTalkAccountProperties dingTalkAccountProperties;
    @Resource
    private AuthService authService;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String path = requestTemplate.path();
        List<String> whiteList = dingTalkAccountProperties.getWhiteList();
        if (StringUtils.matches(path, whiteList)) {
            return;
        }
        // 不在白名单中请求路径则需要添加token访问
        // 获取token
        String token = authService.getAuthToken();
        // 设置到请求头
        requestTemplate.header("Blade-Auth", token);

    }
}
