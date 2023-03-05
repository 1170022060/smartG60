package com.ruoyi.job.task.algorithmBeiJing;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteAlgorithmBeiJingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 北京算法定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("algorithmBeiJingTask")
public class AlgorithmBeiJingTask {
    @Autowired
    private RemoteAlgorithmBeiJingService remoteAlgorithmBeiJingService;

    public void gantryTransactionLog() {
        String dateTime = DateUtils.dateTime(DateUtils.getPreTime(DateUtils.getNowDate(),-2),DateUtils.YYYY_MM_DD_HH_MM);
        R r = remoteAlgorithmBeiJingService.gantryTransactionLog(dateTime+":00",dateTime+":59");
        if (r.getCode() == R.SUCCESS) {
            log.info("gantryTransactionLog定时任务执行成功");
        } else {
            log.info("gantryTransactionLog定时任务执行失败：" + r.getMsg());
        }
    }

}
