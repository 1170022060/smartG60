package com.ruoyi.job.task.infoBoard;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVmsMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("infoBoardTask")
public class InfoBoardTask {

    @Autowired
    private RemoteVmsMonitorService remoteVmsMonitorService;

    public void collect() {
        R r = remoteVmsMonitorService.collect();
        if(r.getCode() == 200) {
            log.info("情报板定时任务成功.");
        } else {
            log.error("情报板定时任务失败: " + r.getMsg());
        }
    }
}
