package com.pingok.devicemonitor.controller.gantry;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.gantry.TblGantryEventRelease;
import com.pingok.devicemonitor.service.gantry.IGantryService;
import com.pingok.devicemonitor.service.gantry.IGantryUpperService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/5/23 12:35
 */
@RestController
@RequestMapping("/gantryUpper")
public class GantryUpperController {
    @Autowired
    private IGantryUpperService iGantryUpperService;

    @Autowired
    private IGantryService iGantryService;


    @RequiresPermissions("deviceMonitor:gantryUpper:eventProcessing")
    @Log(title = "门架管理", businessType = BusinessType.OTHER)
    @PostMapping("/eventProcessing")
    public AjaxResult eventProcessing(@RequestBody TblGantryEventRelease tblGantryEventRelease) {
        if (StringUtils.isNull(tblGantryEventRelease)) {
            AjaxResult.error("请求参数不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getStakeNum())) {
            AjaxResult.error("桩号不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getDirection())) {
            AjaxResult.error("方向不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getEventType())) {
            AjaxResult.error("事件播报类型不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getEventId())) {
            AjaxResult.error("事件类型编号不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getReportBeginTime())) {
            AjaxResult.error("播报开始时间不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getReportEndTime())) {
            AjaxResult.error("播报结束时间不能为空！");
        }
        if (StringUtils.isNull(tblGantryEventRelease.getCryptoGraphicDigest())) {
            AjaxResult.error("消息摘要不能为空！");
        }
        if (tblGantryEventRelease.getEventType() == "02" && StringUtils.isNull(tblGantryEventRelease.getEventInfo())) {
            AjaxResult.error("事件消息内容不能为空！");
        }
        iGantryService.eventProcessing(tblGantryEventRelease);
        return AjaxResult.success();
    }

    @PostMapping("/baseInfoUpload")
    public AjaxResult handleBaseInfoUpload(@RequestBody JSONObject data) {
        iGantryUpperService.handleBaseInfoUpload(data);
        return AjaxResult.success();
    }

    @PostMapping("/tghbu")
    public AjaxResult handleTghbu(@RequestBody JSONObject data) {
        iGantryUpperService.handleTghbu(data);
        return AjaxResult.success();
    }

    @PostMapping("/specialEventUpload")
    public AjaxResult handleSpecialEventUpload(@RequestBody JSONObject data) {
        iGantryUpperService.handleSpecialEventUpload(data);
        return AjaxResult.success();
    }

}
