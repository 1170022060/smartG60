package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteMonitorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * 监控服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteMonitorService", value = ServiceNameConstants.MONITOR_SERVICE, fallbackFactory = RemoteMonitorFallbackFactory.class)
public interface RemoteMonitorService {
    /**
     * 检查设备在线状态
     *
     * @return 结果
     */
    @PutMapping("/device")
    R checkStatus();


}
