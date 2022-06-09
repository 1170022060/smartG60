package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteAlgorithmService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 参数服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteAlgorithmFallbackFactory implements FallbackFactory<RemoteAlgorithmService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAlgorithmFallbackFactory.class);

    @Override
    public RemoteAlgorithmService create(Throwable throwable) {
        log.error("算法服务调用失败:{}", throwable.getMessage());
        return new RemoteAlgorithmService() {
            @Override
            public R<String> saveAlgVehCarbonEmission() {
                return null;
            }
        };
    }
}
