package com.ruoyi.job.task.monitor;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监控定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("monitorTask")
public class MonitorTask {
    @Autowired
    private RemoteMonitorService remoteMonitorService;

    public void checkStatus() {
        R r = remoteMonitorService.checkStatus();
        if (r == null) {
            log.error("设备在线状态检查任务执行失败：monitor服务无响应");
        } else {
            if (r.getCode() == R.FAIL) {
                log.error("设备在线状态检查任务执行失败：" + r.getMsg());
            }
        }

    }
}
