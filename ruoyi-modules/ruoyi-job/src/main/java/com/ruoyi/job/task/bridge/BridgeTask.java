package com.ruoyi.job.task.bridge;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteBridgeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 桥梁定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("bridgeTask")
public class BridgeTask {
    @Autowired
    private RemoteBridgeService remoteBridgeService;

    public void getWarning() {
        R r = remoteBridgeService.getWarning();
        if (r.getCode() == R.SUCCESS) {
            log.info("获取桥梁预警事件定时任务执行成功");
        } else {
            log.info("获取桥梁预警事件定时任务执行失败：" + r.getMsg());
        }
    }

    public void updateAcquisition() {
        R r = remoteBridgeService.updateAcquisition();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥梁更新传感器信息定时任务执行成功");
        } else {
            log.info("更新桥梁更新传感器信息定时任务执行失败：" + r.getMsg());
        }
    }

    public void updateBridgeInfo() {
        R r = remoteBridgeService.updateBridgeInfo();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥梁基础信息定时任务执行成功");
        } else {
            log.info("更新桥梁基础信息定时任务执行失败：" + r.getMsg());
        }
    }

    public void updateCollection() {
        R r = remoteBridgeService.updateCollection();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥梁采集仪定时任务执行成功");
        } else {
            log.info("更新桥梁采集仪定时任务执行失败：" + r.getMsg());
        }
    }

    public void updateProjectInfo() {
        R r = remoteBridgeService.updateProjectInfo();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥梁项目信息定时任务执行成功");
        } else {
            log.info("更新桥梁项目信息定时任务执行失败：" + r.getMsg());
        }
    }

    public void updateWarning() {
        R r = remoteBridgeService.updateWarning();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥梁预警信息定时任务执行成功");
        } else {
            log.info("更新桥梁预警信息定时任务执行失败：" + r.getMsg());
        }
    }

    public void getTemperature() {
        R r = remoteBridgeService.getTemperature();
        if (r.getCode() == R.SUCCESS) {
            log.info("更新桥面温度数据定时任务执行成功");
        } else {
            log.info("更新桥面温度数据定时任务执行失败：" + r.getMsg());
        }
    }
}
