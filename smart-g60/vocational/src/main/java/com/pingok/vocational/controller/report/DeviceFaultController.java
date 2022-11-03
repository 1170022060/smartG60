package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.TblDeviceFault;
import com.pingok.vocational.domain.report.vo.DeviceFaultSearch;
import com.pingok.vocational.domain.report.vo.DeviceFaultTypeVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.service.report.IDeviceFaultService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备故障记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceFault")
public class DeviceFaultController extends BaseController {

    @Autowired
    private IDeviceFaultService deviceFaultService;

    /**
     * 查询详情
     */
    @GetMapping("/faultStatistics")
    public AjaxResult faultStatistics() {
        return AjaxResult.success(deviceFaultService.faultStatistics());
    }

    /**
     * 新增
     */
    @RequiresPermissions("vocational:deviceFault:add")
    @Log(title = "设备故障管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add(@RequestBody TblDeviceFault tblDeviceFault) {
        deviceFaultService.add(tblDeviceFault);
        return AjaxResult.success();
    }

    /**
     * 查询详情
     */
    @GetMapping("/findById")
    public AjaxResult findById(@RequestParam Long id) {
        return AjaxResult.success(deviceFaultService.findById(id));
    }

    /**
     * 故障解除
     */
    @RequiresPermissions("vocational:deviceFault:relieve")
    @Log(title = "设备故障管理", businessType = BusinessType.UPDATE)
    @PutMapping("/relieve")
    public AjaxResult relieve(@RequestParam Long id, String remark) {
        deviceFaultService.relieve(id, remark);
        return AjaxResult.success();
    }


    /**
     * 故障确认
     */
    @RequiresPermissions("vocational:deviceFault:confirm")
    @Log(title = "设备故障管理", businessType = BusinessType.UPDATE)
    @PutMapping("/confirm")
    public AjaxResult confirm(@RequestParam Long id, String remark) {
        deviceFaultService.confirm(id, remark);
        return AjaxResult.success();
    }

    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping("/search")
    public TableDataInfo search(String faultType, Long deviceId, String faultId, String faultDescription, Integer status) {
        startPage();
        List<DeviceFaultSearch> list = deviceFaultService.search(faultType, deviceId, faultId, faultDescription, status);
        return getDataTable(list);
    }

    @GetMapping("/type")
    public TableDataInfo type(@RequestParam(name = "faultType", required = false) String faultType, @RequestParam(name = "startTime", required = false) Date startTime, @RequestParam(name = "endTime", required = false) Date endTime) {
        startPage();
        List<Map> type = deviceFaultService.selectDeviceFaultByType
                (faultType, startTime, endTime);
        return getDataTable(type);
    }

    @PostMapping("/exportType")
    @ResponseBody
    public void exportType(HttpServletResponse response, @RequestBody ReportVo reportVo) {
        List<DeviceFaultTypeVo> list = deviceFaultService.selectDeviceFaultByTypeList(reportVo);
        ExcelUtil<DeviceFaultTypeVo> util = new ExcelUtil<DeviceFaultTypeVo>(DeviceFaultTypeVo.class);
        util.exportExcel(response, list, "故障统计(按故障类型)");
    }

    @GetMapping("/countFaultByDevice")
    public TableDataInfo getFaultByDevice(String deviceName,Date startTime,Date endTime){
        startPage();
        List<Map> info = deviceFaultService.selectFaultByDeviceName(deviceName,startTime,endTime);
        return getDataTable(info);
    }
    @GetMapping("/countFaultByDevicetype")
    public TableDataInfo getFaultByDeviceType(Long deviceTypeId, Date startTime, Date endTime){
        startPage();
        List<Map> info = deviceFaultService.selectFaultByDeviceType(deviceTypeId,startTime,endTime);
        return getDataTable(info);
    }

    @GetMapping("/countFaultByFaultType")
    public TableDataInfo getFaultByFaultType(Integer faultType, Date startTime, Date endTime){
        startPage();
        List<Map> info = deviceFaultService.selectFaultByFaultType(faultType,startTime,endTime);
        return getDataTable(info);
    }
}
