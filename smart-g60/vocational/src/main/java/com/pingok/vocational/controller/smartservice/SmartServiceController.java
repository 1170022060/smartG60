package com.pingok.vocational.controller.smartservice;

import com.pingok.vocational.domain.device.TblDeviceRepair;
import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.service.smartservice.ISmartService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 服务区 信息操作处理
 *
 * @author xia
 */
@RestController
@RequestMapping("/smartService")
public class SmartServiceController extends BaseController {

    @Autowired
    private ISmartService smartService;

//    @RequiresPermissions("vocational:smartService:device")
    @Log(title = "设备监控-服务区", businessType = BusinessType.OTHER)
    @GetMapping("/device")
    public TableDataInfo service(@RequestParam(name = "fieldNum",required = false) String fieldNum, @RequestParam(name = "deviceType",required = false) Integer deviceType, @RequestParam(name = "status",required = false) Integer status) {
        startPage();
        List<Map> list = smartService.serviceDevice(fieldNum,deviceType,status);
        return getDataTable(list);
    }

    @RequiresPermissions("vocational:smartService:reportFault")
    @Log(title = "设备监控-服务区-报故障", businessType = BusinessType.INSERT)
    @PostMapping("/reportFault")
    public AjaxResult fault(@Validated @RequestBody TblDeviceFault tblDeviceFault)
    {
        return toAjax(smartService.insertServiceFault(tblDeviceFault));
    }

//    @RequiresPermissions("vocational:smartService:status")
    @Log(title = "设备监控-服务区设备状态", businessType = BusinessType.OTHER)
    @GetMapping("/status")
    public TableDataInfo serviceStatus(@RequestParam(name = "fieldNum",required = false) String fieldNum,@RequestParam(name = "deviceType",required = false) Integer deviceType) {
        startPage();
        List<Map> list = smartService.serviceDeviceStatus(fieldNum,deviceType);
        return getDataTable(list);
    }

//    @RequiresPermissions("vocational:smartService:serviceFault")
    @Log(title = "设备监控-服务区设备故障", businessType = BusinessType.OTHER)
    @GetMapping("/serviceFault")
    public TableDataInfo serviceFault(@RequestParam(name = "fieldNum",required = false) String fieldNum,@RequestParam(name = "deviceType",required = false) Integer deviceType ,@RequestParam(name = "deviceId",required = false) String deviceId,@RequestParam(name = "faultId",required = false) String faultId,@RequestParam(name = "faultDescription",required = false) String faultDescription, @RequestParam(name = "status",required = false) Integer status) {
        startPage();
        List<Map> list = smartService.serviceDeviceFault(fieldNum,  deviceType, deviceId,  faultId,  faultDescription, status);
        return getDataTable(list);
    }

    @RequiresPermissions("vocational:smartService:repair")
    @Log(title = "设备监控-服务区-修复结果填报", businessType = BusinessType.INSERT)
    @PostMapping("/repair")
    public AjaxResult repair(@Validated @RequestBody TblDeviceRepair tblDeviceRepair)
    {
        return toAjax(smartService.insertServiceRepair(tblDeviceRepair));
    }

    @Log(title = "设备监控-服务区-设备状态", businessType = BusinessType.OTHER)
    @GetMapping("/getDeviceStatus")
    public AjaxResult getDeviceStatusDesc()
    {
        return AjaxResult.success(smartService.getDeviceStatusDesc());
    }

}
