package com.ruoyi.job.task.primary;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemotePrimaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("primaryTask")
public class PrimaryTask {

    @Resource
    private RemotePrimaryService remotePrimaryService;

    public void getLargeTransport() {
        R r = remotePrimaryService.largeTransportCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getLargeTransport----成功");
        } else {
            log.error("primaryTask定时任务getLargeTransport----失败：" + r.getMsg());
        }
    }

    public void getOlFlowAndRate() {
        R r = remotePrimaryService.olFlowAndRateCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getOlFlowAndRate----成功");
        } else {
            log.error("primaryTask定时任务getOlFlowAndRate----失败：" + r.getMsg());
        }
    }

    public void getOlFlowInfo() {
        R r = remotePrimaryService.olFlowCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getOlFlowInfo----成功");
        } else {
            log.error("primaryTask定时任务getOlFlowInfo----失败：" + r.getMsg());
        }
    }

    public void getOlWeightAndRate() {
        R r = remotePrimaryService.olWeightAndRateCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getOlWeightAndRate----成功");
        } else {
            log.error("primaryTask定时任务getOlWeightAndRate----失败：" + r.getMsg());
        }
    }

    public void getStationLLInfo() {
        R r = remotePrimaryService.stationLLInfoCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getStationLLInfo----成功");
        } else {
            log.error("primaryTask定时任务getStationLLInfo----失败：" + r.getMsg());
        }
    }

    public void getTotalWeightOver100() {
        R r = remotePrimaryService.totalWeightOver100Collect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getTotalWeightOver100----成功");
        } else {
            log.error("primaryTask定时任务getTotalWeightOver100----失败：" + r.getMsg());
        }
    }

    public void getTruckOW() {
        R r = remotePrimaryService.truckOWCollect();
        if (r.getCode() == 200) {
            log.info("primaryTask定时任务getTruckOW----成功");
        } else {
            log.error("primaryTask定时任务getTruckOW----失败：" + r.getMsg());
        }
    }
}
