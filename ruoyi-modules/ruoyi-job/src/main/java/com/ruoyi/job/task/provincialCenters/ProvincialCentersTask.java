package com.ruoyi.job.task.provincialCenters;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteDataCenterService;
import com.ruoyi.system.api.RemoteGpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * GPS定时任务调度
 *
 * @author qiumin
 */
@Slf4j
@Component("provincialCentersTask")
public class ProvincialCentersTask {
    @Autowired
    private RemoteDataCenterService remoteDataCenterService;

    public void sharEnpdResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60ENPD_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharEnpdResSender定时任务执行成功");
        } else {
            log.info("sharEnpdResSender定时任务执行失败：" + r.getMsg());
        }
    }

    public void sharOtdResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60OTD_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharOtdResSender定时任务执行成功");
        } else {
            log.info("sharOtdResSender定时任务执行失败：" + r.getMsg());
        }
    }

    public void sharEtctdResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60ETCTD_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharEtctdResSender定时任务执行成功");
        } else {
            log.info("sharEtctdResSender定时任务执行失败：" + r.getMsg());
        }
    }

    public void sharSvidResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60SVID_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharSvidResSender定时任务执行成功");
        } else {
            log.info("sharSvidResSender定时任务执行失败：" + r.getMsg());
        }
    }

    public void sharGtdResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60GTD_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharGtdResSender定时任务执行成功");
        } else {
            log.info("sharGtdResSender定时任务执行失败：" + r.getMsg());
        }
    }

    public void sharGvidResSender() {
        R r = remoteDataCenterService.getData("SHARE_G60GVID_REQ_SENDER", DateUtils.parseDateToStr(DateUtils.YYYY_MM_DD_HH_MM_SS,DateUtils.getPreTime(DateUtils.getNowDate(),-1)));
        if (r.getCode() == R.SUCCESS) {
            log.info("sharGvidResSender定时任务执行成功");
        } else {
            log.info("sharGvidResSender定时任务执行失败：" + r.getMsg());
        }
    }
}
