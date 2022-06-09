package com.ruoyi.system.api.factory;

import com.ruoyi.system.api.RemoteIdProducerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 唯一ID服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteIdProducerbackFactory implements FallbackFactory<RemoteIdProducerService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteIdProducerbackFactory.class);

    @Override
    public RemoteIdProducerService create(Throwable throwable) {
        log.error("唯一ID服务调用失败:{}", throwable.getMessage());
        return new RemoteIdProducerService() {


            @Override
            public Long nextId() {
                return null;
            }
        };

    }
}
