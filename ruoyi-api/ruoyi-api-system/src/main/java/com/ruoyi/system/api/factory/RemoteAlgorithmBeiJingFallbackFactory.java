package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteAlgorithmBeiJingService;
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
public class RemoteAlgorithmBeiJingFallbackFactory implements FallbackFactory<RemoteAlgorithmBeiJingService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAlgorithmBeiJingFallbackFactory.class);

    @Override
    public RemoteAlgorithmBeiJingService create(Throwable throwable) {
        log.error("算法服务调用失败:{}", throwable.getMessage());
        return new RemoteAlgorithmBeiJingService() {
            @Override
            public R gantryTransactionLog(String startTime, String endTime) {
                return null;
            }
        };
    }
}
