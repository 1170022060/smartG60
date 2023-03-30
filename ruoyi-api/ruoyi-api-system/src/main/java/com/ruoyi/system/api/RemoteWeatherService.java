package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteWeatherFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 气象服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteWeatherService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteWeatherFallbackFactory.class)
public interface RemoteWeatherService {


    @GetMapping("/weather")
    R getWeather();
}
