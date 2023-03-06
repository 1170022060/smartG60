package com.ruoyi.job.task.vdt;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVdtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component("vdtTask")
public class VdtTask {
    @Resource
    private RemoteVdtService remoteVdtService;

    public void getVdtFlow(){
        R r = remoteVdtService.vdtCollect();

        if (r.getCode()==200){
            log.info("vdtTask定时任务getVdtFlow----成功");
        }else{
            log.error("vdtTask定时任务getVdtFlow----失败：",r.getMsg());
        }
    }
}
