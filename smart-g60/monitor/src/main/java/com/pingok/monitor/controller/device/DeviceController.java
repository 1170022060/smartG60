package com.pingok.monitor.controller.device;

import com.pingok.monitor.service.device.IDeviceStatusService;
import com.pingok.monitor.service.deviceInfo.IDeviceInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequiresPermissions("monitor:device:search")
    @Log(title = "设备监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/search")
    public TableDataInfo search(Long deviceCategory) {
        startPage();
        List<Map> list = iDeviceStatusService.list(deviceCategory);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:device:findByFieldNum")
    @Log(title = "设备监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByFieldNum(@RequestParam String fieldNum) {
        return AjaxResult.success(iDeviceInfoService.findByFieldNum(fieldNum));
    }
}
