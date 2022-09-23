package com.ruoyi.job.task.rush;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteDataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 闯关分拣定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("rush")
public class RushTask {
    @Autowired
    private RemoteDataCenterService remoteDataCenterService;

    public void rushRecord() {
        String year = DateUtils.dateYear();
        String startTime = DateUtils.getTimeHour() + ":00:00";
        String endTime = DateUtils.getTimeHour() + ":59:59";
        String twoHours = DateUtils.dateTime(DateUtils.getPreTime(DateUtils.getNowDate(),-120),DateUtils.YYYYMMDDHHMM) + ":00:00";
        R r = remoteDataCenterService.rushRecord(year, startTime, endTime,twoHours);
        if (r.getCode() == R.SUCCESS) {
            log.info("闯关分拣定时任务执行成功");
        } else {
            log.info("闯关分拣定时任务执行失败：" + r.getMsg());
        }
    }
}
