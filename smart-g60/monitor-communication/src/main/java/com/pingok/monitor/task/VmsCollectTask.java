package com.pingok.monitor.task;

import cn.hutool.core.date.DateTime;
import com.pingok.monitor.service.infoboard.IVmsService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author
 * @time 2022/6/26 21:22
 */
@Slf4j
@Configuration
@EnableScheduling
@Component("vmsCollectTask")
public class VmsCollectTask {
    @Autowired
    private IVmsService iVmsService;

    /**
     * 情报板采集服务，5分钟
     */
//    @Scheduled(cron = "* 0/5 * * * *")
//    @Scheduled(cron = "*/30 * * * * *")
//    private void collect() {
//        try {
//            log.info("情报板状态开始采集：" + DateUtils.dateTimeNow());
//            iVmsService.collect();
//        } catch (Exception e) {
//            log.error("情报板状态采集任务失败：" + e.getMessage());
//        }
//    }
}
