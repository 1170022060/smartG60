package com.ruoyi.job.task.deviceMonitor;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author
 * @time 2022/4/13 16:58
 */
@Slf4j
@Component("deviceMonitorTask")
public class DeviceMonitorTask {
    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    public void serverHeartbeat() {
        R r = remoteDeviceMonitorService.serverHeartbeat();
        if (r.getCode() == 200) {
            log.info("deviceMonitorTask定时任务serverHeartbeat----成功");
        } else {
            log.error("deviceMonitorTask定时任务serverHeartbeat----失败：" + r.getMsg());
        }
    }
}