package com.pingok.station.task.auditList;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.auditList.IAuditListService;
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
@Component("auditListTask")
public class AuditListTask {
    @Autowired
    private IAuditListService auditListService;
    @Autowired
    private ListTracerMapper listTracerMapper;
    /**
     * 稽核黑名单增量定时任务
     */
    @Scheduled(cron = "0 1/5 * * * ?")
    private void increment() {
        try {
            String version = DateUtils.getTimeMinute(DateUtils.getPreTime(DateUtils.getNowDate(),-6));
            String versionNow=listTracerMapper.selectVersion("auditlist");
            log.info("AuditListTask开始执行increment任务，版本号为：" + version);
            auditListService.increment(version);
            log.info("AuditListTask执行increment任务成功");
        } catch (Exception e) {
            log.error("AuditListTask执行increment任务失败：" + e.getMessage());
        }
    }
}
