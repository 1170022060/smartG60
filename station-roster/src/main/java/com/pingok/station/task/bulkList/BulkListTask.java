package com.pingok.station.task.bulkList;

import com.pingok.station.mapper.tracer.ListTracerMapper;
import com.pingok.station.service.bulkList.IBulkListService;
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
@Component("bulkListTask")
public class BulkListTask {
    @Autowired
    private IBulkListService bulkListService;
    @Autowired
    private ListTracerMapper listTracerMapper;

    /**
     * 大件运输名单全量定时任务
     */
    @Scheduled(cron = "0 1/10 * * * ?")
    private void bulkList() {
        try {
            String version = DateUtils.getTimeMinute(DateUtils.getPreTime(DateUtils.getNowDate(),-11));
            String versionNow=listTracerMapper.selectVersion("bulklist");
            log.info("BulkListTask开始执行bulkList任务，版本号为：" + version);
            bulkListService.bulkList(version);
            log.info("BulkListTask执行bulkList任务成功");
        } catch (Exception e) {
            log.error("BulkListTask执行bulkList任务失败：" + e.getMessage());
        }
    }
}
