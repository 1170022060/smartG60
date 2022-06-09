package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author
 * @time 2022/4/13 17:00
 */
@Component
public class RemoteDeviceMonitorFallbackFactory implements FallbackFactory<RemoteDeviceMonitorService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteDeviceMonitorFallbackFactory.class);

    @Override
    public RemoteDeviceMonitorService create(Throwable throwable) {
        log.error("设备监控服务调用失败:{}", throwable.getMessage());
        return new RemoteDeviceMonitorService() {

            @Override
            public R serverHeartbeat() {
                return null;
            }
        };

    }
}
