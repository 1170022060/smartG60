package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.device.TblDeviceStatus;
import com.ruoyi.system.api.factory.RemoteDeviceMonitorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId = "remoteDeviceMonitorService", value = ServiceNameConstants.DEVICE_MONITOR_SERVICE, fallbackFactory = RemoteDeviceMonitorFallbackFactory.class)
public interface RemoteDeviceMonitorService {

    /**
     * 服务器心跳接口
     *
     * @return
     */
    @GetMapping("/deviceMonitor")
    R serverHeartbeat();

    @GetMapping("/deviceMonitor/selectByDeviceId")
    R<TblDeviceInfo> selectByDeviceId(@RequestParam(value = "deviceId") String deviceId);

    @PostMapping("/deviceMonitor")
    R updateHeartbeat(@RequestBody TblDeviceStatus deviceStatus);

}
