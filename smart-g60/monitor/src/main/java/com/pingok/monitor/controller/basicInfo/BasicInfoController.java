package com.pingok.monitor.controller.basicInfo;

import com.pingok.monitor.domain.vo.BasicInfo;
import com.pingok.monitor.service.baseStation.IBaseStationInfoService;
import com.pingok.monitor.service.deviceInfo.IDeviceInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 基础 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/basicInfo")
public class BasicInfoController extends BaseController {

    @Autowired
    private IBaseStationInfoService iBaseStationInfoService;
    @Autowired
    private IDeviceInfoService iDeviceInfoService;

    @RequiresPermissions("monitor:basicInfo:findByUser")
    @Log(title = "收费站监控服务", businessType = BusinessType.OTHER)
    @GetMapping(value = "/findByUser")
    public AjaxResult findByUser() {
        return AjaxResult.success(iBaseStationInfoService.findByUser());
    }

    @Log(title = "监控大屏服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult getInfo() {
        BasicInfo basicInfo = new BasicInfo();
        basicInfo.setBaseStationInfos(iBaseStationInfoService.getMonitorInfo());
        basicInfo.setMonitorDeviceInfos(iDeviceInfoService.getInfo());
        return AjaxResult.success(basicInfo);
    }
}
