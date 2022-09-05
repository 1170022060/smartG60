package com.pingok.station.task.obuBlacklist;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.obuBlacklist.IObuBlacklistService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Configuration
@EnableScheduling
@Component("OBUBlacklistTask")
public class OBUBlacklistTask {

    @Autowired
    private IObuBlacklistService iObuBlacklistService;
    @Autowired
    private ListTracerMapper listTracerMapper;

    /**
     * OBU名单增量定时任务
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    private void increment() {
        try {
            String version = DateUtils.getTimeMinute(DateUtils.getPreTime(DateUtils.getNowDate(),-5));
            String versionNow=listTracerMapper.selectVersion("obuBlacklist");
            log.info("OBUBlacklistTask开始执行increment任务，版本号为：" + version);
            iObuBlacklistService.increment(version);
            log.info("OBUBlacklistTask执行increment任务成功");
        } catch (Exception e) {
            log.error("OBUBlacklistTask执行increment任务失败：" + e.getMessage());
        }
    }

}
