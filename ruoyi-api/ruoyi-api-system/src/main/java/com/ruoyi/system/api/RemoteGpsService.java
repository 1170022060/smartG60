package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteGpsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * GPS服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteGpsService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteGpsFallbackFactory.class)
public interface RemoteGpsService {


    @GetMapping("/gps")
    R getCarsGps();
}
