package com.ruoyi.job.task.roadDoctor;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteMonitorService;
import com.ruoyi.system.api.RemoteRoadDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 道路医生定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("roadDoctoTask")
public class RoadDoctorTask {
    @Autowired
    private RemoteRoadDoctorService remoteRoadDoctorService;

    public void updateDisease() {
        R r = remoteRoadDoctorService.updateDisease(SecurityConstants.INNER);
        if (r == null) {
            log.error("道路病害更新任务执行失败：external-system服务无响应");
        } else {
            if (r.getCode() == R.FAIL) {
                log.error("道路病害更新任务执行失败：" + r.getMsg());
            }
        }

    }
}
