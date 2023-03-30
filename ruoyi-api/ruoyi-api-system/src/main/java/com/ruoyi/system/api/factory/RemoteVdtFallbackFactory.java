package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVdtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoteVdtFallbackFactory implements FallbackFactory<RemoteVdtService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteVdtFallbackFactory.class);

    @Override
    public RemoteVdtService create(Throwable cause) {
        log.error("车检器流量数据采集服务调用失败：{}",cause.getMessage());
        return new RemoteVdtService() {
            @Override
            public R vdtCollect() {
                return null;
            }
        };
    }
}
