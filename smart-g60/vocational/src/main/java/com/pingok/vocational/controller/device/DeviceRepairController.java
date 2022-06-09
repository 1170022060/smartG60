package com.pingok.vocational.controller.device;

import com.pingok.vocational.service.device.IDeviceRepairService;
import com.ruoyi.common.core.web.controller.BaseController;
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
 * 设备维修记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceRepair")
public class DeviceRepairController extends BaseController {

    @Autowired
    private IDeviceRepairService deviceRepairService;

    @RequiresPermissions("vocational:deviceRepair:info")
    @Log(title = "设备维修记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "deviceId") Long deviceId)
    {
        startPage();
        List<Map> info = deviceRepairService.selectDeviceRepair(deviceId);
        return getDataTable(info);
    }
}
