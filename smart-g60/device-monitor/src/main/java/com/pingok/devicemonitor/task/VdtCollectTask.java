//package com.pingok.devicemonitor.task;
//
//import com.pingok.devicemonitor.service.vdt.IVdtService;
//import com.ruoyi.common.core.utils.DateUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Configuration
//@EnableScheduling
//@Component("vdtCollectTask")
//public class VdtCollectTask {
//    @Autowired
//    private IVdtService iVdtService;
//
//    /**
//     * 车检器采集服务，5分钟
//     */
//    @Scheduled(cron = "* 0/5 * * * *")
////    @Scheduled(cron = "*/30 * * * * *")
//    private void collect() {
//        try {
//            log.info("车检器状态开始采集：" + DateUtils.dateTimeNow());
//            iVdtService.collect();
//        } catch (Exception e) {
//            log.error("车检器状态采集任务失败：" + e.getMessage());
//        }
//    }
//}
