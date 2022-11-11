package com.ruoyi.job.task.artemis;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteArtemisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author
 * @time 2022/05/07 16:58
 */
@Slf4j
@Component("artemisTask")
public class ArtemisTask {

    @Resource
    private RemoteArtemisService remoteArtemisService;

    public void updateStatus() {
        R r = remoteArtemisService.updateStatus();
        if (r.getCode() == 200) {
            log.info("执法记录仪定时任务updateStatus----成功");
        } else {
            log.error("执法记录仪定时任务updateStatus----失败：" + r.getMsg());
        }
    }
}