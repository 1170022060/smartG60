package com.ruoyi.job.task.gps;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteGpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GPS定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("gpsTask")
public class GpsTask {
    @Autowired
    private RemoteGpsService remoteGpsService;

    public void getCarsGps() {
        R r = remoteGpsService.getCarsGps();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新养护车辆GPS数据定时任务执行成功");
        } else {
            log.info("更新养护车辆GPS数据定时任务执行失败：" + r.getMsg());
        }
    }
}
