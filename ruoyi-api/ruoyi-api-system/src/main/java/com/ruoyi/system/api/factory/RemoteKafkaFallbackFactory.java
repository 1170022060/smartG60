package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * kafka服务降级处理
 * 
 * @author qiumin
 */
@Component
public class RemoteKafkaFallbackFactory implements FallbackFactory<RemoteKafkaService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteKafkaFallbackFactory.class);

    @Override
    public RemoteKafkaService create(Throwable throwable)
    {
        log.error("kafka服务调用失败:{}", throwable.getMessage());
        return new RemoteKafkaService()
        {

            @Override
            public R send(KafkaEnum kafkaEnum) {
                return null;
            }

        };

    }
}
