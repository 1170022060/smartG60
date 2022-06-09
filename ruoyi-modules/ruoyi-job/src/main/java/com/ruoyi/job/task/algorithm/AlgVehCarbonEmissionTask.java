package com.ruoyi.job.task.algorithm;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteAlgorithmService;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author
 * @time 2022/05/07 16:58
 */
@Slf4j
@Component("algVehCarbonEmissionTask")
public class AlgVehCarbonEmissionTask {

    @Resource
    private RemoteAlgorithmService remoteAlgorithmService;

    public void saveAlgVehCarbonEmission() {
        R r = remoteAlgorithmService.saveAlgVehCarbonEmission();
        if (r.getCode() == 200) {
            log.info("algVehCarbonEmissionTask定时任务saveAlgVehCarbonEmission----成功");
        } else {
            log.error("algVehCarbonEmissionTask定时任务saveAlgVehCarbonEmission----失败：" + r.getMsg());
        }
    }
}