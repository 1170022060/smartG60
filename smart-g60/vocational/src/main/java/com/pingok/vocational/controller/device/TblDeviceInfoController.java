package com.pingok.vocational.controller.device;

import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.domain.device.TblDeviceInfo;
import com.pingok.vocational.service.device.IDeviceStatusService;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
import com.pingok.vocational.service.device.TblDeviceInfoService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 设备信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/device")
public class TblDeviceInfoController extends BaseController {
    @Autowired
    private TblDeviceInfoService tblDeviceInfoService;
    @Autowired
    private IDeviceStatusService deviceStatusService;
    @Autowired
    private TblDeviceCategoryService tblDeviceCategoryService;


    @GetMapping(value = "/findByCategoryNum")
    public AjaxResult findByCategoryNum(@RequestParam String categoryNum) {
        List<TblDeviceCategory> list = tblDeviceCategoryService.findByNum(categoryNum);
        for (TblDeviceCategory c : list) {
            c.setDeviceInfos(tblDeviceInfoService.findBydeviceCategory(c.getId()));
        }
        return AjaxResult.success(tblDeviceCategoryService.buildMenuTree(list));
    }

    @RequiresPermissions("vocational:device:idInfo")
    @Log(title = "设备信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value = "/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id) {
        TblDeviceInfo idInfo = tblDeviceInfoService.selectDeviceInfoById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:device:info")
    @Log(title = "设备信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "deviceCategory", required = false) Long deviceCategory, @RequestParam(name = "status", required = false) Integer status, @RequestParam(name = "fieldBelong", required = false) Long fieldBelong, @RequestParam(name = "deviceId", required = false) String deviceId, @RequestParam(name = "userSide", required = false) Long userSide, @RequestParam(name = "managementSide", required = false) Long managementSide, @RequestParam(name = "serviceLife", required = false) Integer serviceLife, @RequestParam(name = "deviceName", required = false) String deviceName) {
        startPage();
        List<Map> info = tblDeviceInfoService.selectDeviceInfo(deviceCategory, status, fieldBelong, deviceId, userSide, managementSide, serviceLife, deviceName);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:device:add")
    @Transactional
    @Log(title = "设备基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblDeviceInfo tblDeviceInfo) {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoService.checkDeviceIdUnique(tblDeviceInfo))) {
            return AjaxResult.error("新增设备编号'" + tblDeviceInfo.getDeviceId() + "'失败，设备编号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoService.checkDeviceNameUnique(tblDeviceInfo))) {
            return AjaxResult.error("新增设备名称'" + tblDeviceInfo.getDeviceName() + "'失败，设备名称已存在");
        }
        int result = tblDeviceInfoService.insertDeviceInfo(tblDeviceInfo);
        deviceStatusService.insertDeviceStatus(tblDeviceInfo.getId());
        return toAjax(result);
    }

    @RequiresPermissions("vocational:device:edit")
    @Log(title = "设备基础信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblDeviceInfo tblDeviceInfo) {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoService.checkDeviceIdUnique(tblDeviceInfo))) {
            return AjaxResult.error("修改设备编号'" + tblDeviceInfo.getDeviceId() + "'失败，设备编号已存在");
        } else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoService.checkDeviceNameUnique(tblDeviceInfo))) {
            return AjaxResult.error("修改设备名称'" + tblDeviceInfo.getDeviceName() + "'失败，设备名称已存在");
        }
        return toAjax(tblDeviceInfoService.updateDeviceInfo(tblDeviceInfo));
    }

//    @RequiresPermissions("vocational:device:board")
    @Log(title = "情报板信息查询", businessType = BusinessType.OTHER)
    @GetMapping("/board")
    public TableDataInfo board(@RequestParam(name = "deviceCategory", required = false) Long deviceCategory,@RequestParam(name = "deviceName", required = false) String deviceName,@RequestParam(name = "pileNo", required = false) String pileNo,@RequestParam(name = "manufacturer", required = false) String manufacturer,@RequestParam(name = "deviceModel", required = false) String deviceModel) {
        startPage();
        List<Map> info = tblDeviceInfoService.selectInfoBoard(deviceCategory, deviceName, pileNo, manufacturer, deviceModel);
        return getDataTable(info);
    }
}
