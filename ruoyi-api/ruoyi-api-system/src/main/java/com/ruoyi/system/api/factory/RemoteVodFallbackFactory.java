package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVodService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * vod服务降级处理
 * 
 * @author qiumin
 */
@Component
public class RemoteVodFallbackFactory implements FallbackFactory<RemoteVodService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteVodFallbackFactory.class);

    @Override
    public RemoteVodService create(Throwable throwable)
    {
        log.error("vod服务调用失败:{}", throwable.getMessage());
        return new RemoteVodService()
        {

            @Override
            public R updateCameraList() {
                return null;
            }

            @Override
            public R heartbeat() {
                return null;
            }
        };

    }
}
