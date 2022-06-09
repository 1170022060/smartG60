package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.SysLogininfor;
import com.ruoyi.system.api.domain.SysOperLog;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

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
            public R send(TblKafkaFailInfo tblKafkaFailInfo) {
                return null;
            }

            @Override
            public R<List<TblKafkaFailInfo>> findAll() {
                return null;
            }

            @Override
            public R delete(Long id) {
                return null;
            }
        };

    }
}
