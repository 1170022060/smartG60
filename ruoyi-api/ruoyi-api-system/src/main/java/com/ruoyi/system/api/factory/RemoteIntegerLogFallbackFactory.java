package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteInternetLogService;
import com.ruoyi.system.api.domain.internet.InternetLogininfor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 第三方日志服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteIntegerLogFallbackFactory implements FallbackFactory<RemoteInternetLogService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteIntegerLogFallbackFactory.class);

    @Override
    public RemoteInternetLogService create(Throwable throwable) {
        log.error("第三方日志服务调用失败:{}", throwable.getMessage());
        return new RemoteInternetLogService() {

            @Override
            public R<Boolean> saveLogininfor(InternetLogininfor logininfor, String source) {
                return null;
            }
        };

    }
}
