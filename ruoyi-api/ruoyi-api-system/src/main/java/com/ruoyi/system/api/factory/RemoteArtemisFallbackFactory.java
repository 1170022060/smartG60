package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteArtemisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 执法记录仪服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteArtemisFallbackFactory implements FallbackFactory<RemoteArtemisService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteArtemisFallbackFactory.class);

    @Override
    public RemoteArtemisService create(Throwable throwable) {
        log.error("执法记录仪服务调用失败:{}", throwable.getMessage());
        return new RemoteArtemisService() {

            @Override
            public R updateStatus() {
                return null;
            }
        };

    }
}
