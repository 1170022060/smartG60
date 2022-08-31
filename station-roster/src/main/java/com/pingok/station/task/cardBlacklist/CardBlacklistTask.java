package com.pingok.station.task.cardBlacklist;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.cardBlacklist.ICardBlacklistService;
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
@Component("cardBlackIncrTask")
public class CardBlacklistTask {

    @Autowired
    private ICardBlacklistService cardBlacklistService;
    @Autowired
    private ListTracerMapper listTracerMapper;

    /**
     * 卡黑名单增量定时任务
     */
    @Scheduled(cron = "0 0/5 * * * ?")
    private void increment() {
        try {
//            String version = DateUtils.getTimeMinute(DateUtils.getPreTime(DateUtils.getNowDate(),-5));
            String versionNow=listTracerMapper.selectVersion("blacklist");
            String version = DateUtils.getTimeMinute(DateUtils.getNextMillisEndWithMinute0or5(5,DateUtils.getNowDate()));
            log.info("CardBlacklistTask开始执行increment任务，版本号为：" + version);
            cardBlacklistService.increment(version);
            log.info("CardBlacklistTask执行increment任务成功");
        } catch (Exception e) {
            log.error("CardBlacklistTask执行increment任务失败：" + e.getMessage());
        }
    }

}
