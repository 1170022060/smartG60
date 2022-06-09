package com.pingok.station.task.greenList;

import com.pingok.station.service.greenList.IGreenListService;
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
@Component("greenListTask")
public class GreenListTask {
    @Autowired
    private IGreenListService greenListService;

    /**
     * 绿通名单全量定时任务
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    private void greenList() {
        try {
            String version = DateUtils.getTimeMinute(DateUtils.getPreTime(DateUtils.getNowDate(),-10));
            log.info("GreenListTask开始执行greenList任务，版本号为：" + version);
            greenListService.greenList(version);
            log.info("GreenListTask执行greenList任务成功");
        } catch (Exception e) {
            log.error("GreenListTask执行greenList任务失败：" + e.getMessage());
        }
    }
}
