package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteVmsMonitorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "remoteVmsMonitorService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemoteVmsMonitorFallbackFactory.class)
public interface RemoteVmsMonitorService {
    /**
     * 车检器状态采集
     */
    @GetMapping("/infoBoard")
    R collect();
}
