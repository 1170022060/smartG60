package com.pingok.monitor.controller.device;

import com.pingok.monitor.domain.device.BaseInfo;
import com.pingok.monitor.service.device.IDeviceStatusService;
import com.pingok.monitor.service.deviceInfo.IDeviceInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/device")
public class DeviceController extends BaseController {

    @Autowired
    private IDeviceInfoService iDeviceInfoService;
    @Autowired
    private IDeviceStatusService iDeviceStatusService;

    @PutMapping
    public AjaxResult checkStatus() {
        iDeviceStatusService.checkStatus();
        return AjaxResult.success();
    }


    @RequiresPermissions("monitor:device:search")
    @Log(title = "设备监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/search")
    public TableDataInfo search(Long deviceCategory, String deviceName, String deviceId) {
        startPage();
        List<Map> list = iDeviceStatusService.list(deviceCategory, deviceName, deviceId);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:device:findByFieldNum")
    @Log(title = "设备监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByFieldNum(@RequestParam String fieldNum) {
        return AjaxResult.success(iDeviceInfoService.findByFieldNum(fieldNum));
    }

    @RequiresPermissions("monitor:device:base")
    @Log(title = "设备监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/base")
    public AjaxResult base() {
        BaseInfo baseInfo =new BaseInfo();
        baseInfo.setBaseStation(iDeviceStatusService.selectBaseStation());
        baseInfo.setBridgeInfo(iDeviceStatusService.selectBridgeInfo());
        baseInfo.setVMSInfo(iDeviceStatusService.selectVMS());
        baseInfo.setVDInfo(iDeviceStatusService.selectVD());
        baseInfo.setCAMInfo(iDeviceStatusService.selectCAM());
        baseInfo.setPilotLightInfo(iDeviceStatusService.selectPilotLight());
        return AjaxResult.success(baseInfo);
    }
}
