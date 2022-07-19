package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteDeviceMonitorFallbackFactory;
import com.ruoyi.system.api.factory.RemoteVdtMonitorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 * @time 2022/7/6 10:14
 */
@FeignClient(contextId = "remoteVdtMonitorService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemoteVdtMonitorFallbackFactory.class)
public interface RemoteVdtMonitorService {
    /**
     * 车检器状态采集
     */
    @GetMapping("/vdtMonitor")
    R collect();
}
