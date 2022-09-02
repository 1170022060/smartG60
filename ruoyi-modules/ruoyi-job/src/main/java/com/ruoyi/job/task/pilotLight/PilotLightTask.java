package com.ruoyi.job.task.pilotLight;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemotePilotLightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component("pilotLightTask")
public class PilotLightTask {
    @Autowired
    private RemotePilotLightService remotePilotLightService;

    public void collectRtStatus() {
        R r = remotePilotLightService.getRtStatus();
        if (r.getCode() == 200) {
            log.info("pilotLightTask 定时任务 collectRtStatus----成功");
        } else {
            log.error("pilotLightTask 定时任务 collectRtStatus----失败：" + r.getMsg());
        }
    }
}
