package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteBridgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * gps服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteBridgeFallbackFactory implements FallbackFactory<RemoteBridgeService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteBridgeFallbackFactory.class);

    @Override
    public RemoteBridgeService create(Throwable throwable) {
        log.error("gps服务调用失败:{}", throwable.getMessage());
        return new RemoteBridgeService() {
            @Override
            public R getWarning() {
                return null;
            }

            @Override
            public R updateAcquisition() {
                return null;
            }

            @Override
            public R updateBridgeInfo() {
                return null;
            }

            @Override
            public R updateCollection() {
                return null;
            }

            @Override
            public R updateProjectInfo() {
                return null;
            }

            @Override
            public R updateWarning() {
                return null;
            }

            @Override
            public R getTemperature() {
                return null;
            }
        };

    }
}
