package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteDeviceMonitorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId = "remoteDeviceMonitorService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemoteDeviceMonitorFallbackFactory.class)
public interface RemoteDeviceMonitorService {

    /**
     * 服务器心跳接口
     *
     * @return
     */
    @GetMapping("/deviceMonitor")
    R serverHeartbeat();

}
