package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.RemoteVmsMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteVmsMonitorFallbackFactory implements FallbackFactory<RemoteVmsMonitorService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteReleaseFallbackFactory.class);

    @Override
    public RemoteVmsMonitorService create(Throwable throwable)
    {
        log.error("情报板状态服务调用失败:{}", throwable.getMessage());
        return () -> null;
    }
}