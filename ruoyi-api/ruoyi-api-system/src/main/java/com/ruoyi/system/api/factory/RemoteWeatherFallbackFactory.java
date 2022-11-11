package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 气象服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteWeatherFallbackFactory implements FallbackFactory<RemoteWeatherService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteWeatherFallbackFactory.class);

    @Override
    public RemoteWeatherService create(Throwable throwable) {
        log.error("气象服务调用失败:{}", throwable.getMessage());
        return new RemoteWeatherService() {

            @Override
            public R getWeather() {
                return null;
            }
        };

    }
}
