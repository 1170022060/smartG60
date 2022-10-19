package com.ruoyi.job.task.smartToilet;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * smartToilet定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("smartToiletTask")
public class SmartToiletTask {
    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    public void timeCalibration() {
        R r = remoteDeviceMonitorService.timeCalibration();
        if (r.getCode() == 200) {
            log.info("smartToiletTask定时任务timeCalibration执行成功");
        } else {
            log.error("smartToiletTask定时任务timeCalibration执行失败，报错信息：" + r.getMsg());
        }
    }

    public void weather() {
        R r = remoteDeviceMonitorService.weather();
        if (r.getCode() == 200) {
            log.info("smartToiletTask定时任务weather执行成功");
        } else {
            log.error("smartToiletTask定时任务weather执行失败，报错信息：" + r.getMsg());
        }
    }

}
