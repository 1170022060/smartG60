package com.ruoyi.job.task.vod;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVodService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * kafka定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("vodTask")
public class VodTask {
    @Autowired
    private RemoteVodService remoteVodService;

    public void updateCameraList() {
        R r = remoteVodService.updateCameraList();
        if (r.getCode() == 200) {
            log.info("vodTask定时任务updateCameraList执行成功");
        } else {
            log.error("vodTask定时任务updateCameraList执行失败，报错信息：" + r.getMsg());
        }
    }

    public void heartbeat() {
        R r = remoteVodService.heartbeat();
        if (r.getCode() == 200) {
            log.info("vodTask定时任务heartbeat执行成功");
        } else {
            log.error("vodTask定时任务heartbeat执行失败，报错信息：" + r.getMsg());
        }
    }
}
