package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteEmergencySuppliesService;
import com.ruoyi.system.api.domain.emergency.TblEmergencySupplies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 应急资源服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteEmergencySuppliesFallbackFactory implements FallbackFactory<RemoteEmergencySuppliesService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteEmergencySuppliesFallbackFactory.class);

    @Override
    public RemoteEmergencySuppliesService create(Throwable throwable) {
        log.error("应急资源服务调用失败:{}", throwable.getMessage());
        return new RemoteEmergencySuppliesService() {

            @Override
            public R<TblEmergencySupplies> idInfo(Long id) {
                return null;
            }
        };

    }
}
