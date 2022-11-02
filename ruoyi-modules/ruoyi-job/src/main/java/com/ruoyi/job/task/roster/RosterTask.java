package com.ruoyi.job.task.roster;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDataCenterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 名单定时任务调度
 *
 * @author xia
 */
@Slf4j
@Component("rosterTask")
public class RosterTask {
    @Autowired
    private RemoteDataCenterService remoteDataCenterService;

    public void blackIncr() {
        R r = remoteDataCenterService.blackIncr();
        if (r.getCode() == 200) {
            log.info("rosterTask定时任务blackIncr----成功");
        } else {
            log.error("rosterTask定时任务blackIncr----失败：" + r.getMsg());
        }
    }

    public void epidemicDownload() {
        R r = remoteDataCenterService.epidemicDownload();
        if (r.getCode() == 200) {
            log.info("rosterTask定时任务epidemicDownload----成功");
        } else {
            log.error("rosterTask定时任务epidemicDownload----失败：" + r.getMsg());
        }
    }

    public void prefixDownload() {
        R r = remoteDataCenterService.prefixDownload();
        if (r.getCode() == 200) {
            log.info("rosterTask定时任务prefixDownload----成功");
        } else {
            log.error("rosterTask定时任务prefixDownload----失败：" + r.getMsg());
        }
    }

    public void recoveryIncr() {
        R r = remoteDataCenterService.recoveryIncr();
        if (r.getCode() == 200) {
            log.info("rosterTask定时任务recoveryIncr----成功");
        } else {
            log.error("rosterTask定时任务recoveryIncr----失败：" + r.getMsg());
        }
    }

    public void rescueIncr() {
        R r = remoteDataCenterService.rescueIncr();
        if (r.getCode() == 200) {
            log.info("rosterTask定时任务rescueIncr----成功");
        } else {
            log.error("rosterTask定时任务rescueIncr----失败：" + r.getMsg());
        }
    }
}
