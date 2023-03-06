package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteVdtFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "remoteVdt",value = ServiceNameConstants.DEVICE_MONITOR_SERVICE,fallbackFactory = RemoteVdtFallbackFactory.class)
public interface RemoteVdtService {

    /**
     * 车检器流量数据采集
     * @return
     */
    @GetMapping("/vdt")
    R vdtCollect();
}
