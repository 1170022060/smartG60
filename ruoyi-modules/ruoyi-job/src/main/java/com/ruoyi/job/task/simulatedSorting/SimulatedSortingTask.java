package com.ruoyi.job.task.simulatedSorting;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteDataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 模拟清分定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("simulatedSortingTask")
public class SimulatedSortingTask {
    @Autowired
    private RemoteDataCenterService remoteDataCenterService;

    public void simulatedSorting() {
        String year = DateUtils.dateYear();
        String startTime = DateUtils.getTimeHour() + ":00:00";
        String endTime = DateUtils.getTimeHour() + ":59:59";
        R r = remoteDataCenterService.simulatedSorting(year, startTime, endTime);
        if (r.getCode() == R.SUCCESS) {
            log.info("模拟清分定时任务执行成功");
        } else {
            log.info("模拟清分定时任务执行失败：" + r.getMsg());
        }
    }
}
