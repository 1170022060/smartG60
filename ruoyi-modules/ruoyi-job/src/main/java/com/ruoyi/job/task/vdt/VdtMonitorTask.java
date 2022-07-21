package com.ruoyi.job.task.vdt;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import com.ruoyi.system.api.RemoteVdtMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 车检器数据采集
 * @author
 * @time 2022/7/6 9:59
 */
@Slf4j
@Component("vdtMonitorTask")
public class VdtMonitorTask {
    @Autowired
    private RemoteVdtMonitorService remoteVdtMonitorService;

    public void collect() {
        R r = remoteVdtMonitorService.collect();
        if(r.getCode() == 200) {
            log.info("车检器定时任务成功.");
        } else {
            log.error("车检器定时任务失败: " + r.getMsg());
        }
    }
}
