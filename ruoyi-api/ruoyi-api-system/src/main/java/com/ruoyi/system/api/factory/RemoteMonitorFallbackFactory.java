package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteMonitorService;
import com.ruoyi.system.api.domain.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 监控服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteMonitorFallbackFactory implements FallbackFactory<RemoteMonitorService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteMonitorFallbackFactory.class);

    @Override
    public RemoteMonitorService create(Throwable throwable) {
        log.error("监控服务调用失败:{}", throwable.getMessage());
        return new RemoteMonitorService() {


            @Override
            public R checkStatus() {
                return null;
            }
        };
    }
}
