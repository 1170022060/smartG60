package com.pingok.devicemonitor.task;

import com.alibaba.fastjson.JSON;
import com.pingok.devicemonitor.service.infoboard.IInfoBoardService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.ip.IpUtils;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private IInfoBoardService iInfoBoardService;

    /**
     * 情报板采集服务，5分钟
     */
//    @Scheduled(cron = "* 0/2 * * * *")
//    @Scheduled(cron = "*/30 * * * * *")
//    private void collect() {
//        iInfoBoardService.collect();
//    }
}
