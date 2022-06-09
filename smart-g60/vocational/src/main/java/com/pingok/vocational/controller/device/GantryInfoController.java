package com.pingok.vocational.controller.device;

import com.pingok.vocational.domain.device.TblGantryInfo;
import com.pingok.vocational.service.device.IGantryInfoService;
import com.pingok.vocational.service.device.IGantryStatusService;
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
 * 门架设备信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceGantry")
public class GantryInfoController extends BaseController {
    @Autowired
    private IGantryInfoService tblDeviceInfoGantryService;
    @Autowired
    private IGantryStatusService gantryStatusService;

    @RequiresPermissions("vocational:deviceGantry:idInfo")
    @Log(title = "门架设备基础信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblGantryInfo idInfo = tblDeviceInfoGantryService.selectDeviceInfoGantryById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:deviceGantry:info")
    @Log(title = "门架设备基础信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "deviceCategory",required = false) Long deviceCategory, @RequestParam(name = "status",required = false) Integer status, @RequestParam(name = "fieldBelong",required = false) Long fieldBelong, @RequestParam(name = "deviceId",required = false) String deviceId, @RequestParam(name = "userSide",required = false) Long userSide, @RequestParam(name = "managementSide",required = false) Long managementSide, @RequestParam(name = "serviceLife",required = false) Integer serviceLife, @RequestParam(name = "deviceName",required = false) String deviceName)
    {
        startPage();
        List<Map> info = tblDeviceInfoGantryService.selectDeviceInfoGantry( deviceCategory,  status,  fieldBelong,  deviceId,  userSide,  managementSide, serviceLife,deviceName);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:deviceGantry:add")
    @Transactional
    @Log(title = "门架设备基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblGantryInfo tblDeviceInfoGantry)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoGantryService.checkDeviceIdUnique(tblDeviceInfoGantry)))
        {
            return AjaxResult.error("新增门架设备编号'" + tblDeviceInfoGantry.getDeviceId() + "'失败，门架设备编号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoGantryService.checkDeviceNameUnique(tblDeviceInfoGantry)))
        {
            return AjaxResult.error("新增门架设备名称'" + tblDeviceInfoGantry.getDeviceName() + "'失败，门架设备名称已存在");
        }
        int result =tblDeviceInfoGantryService.insertDeviceInfoGantry(tblDeviceInfoGantry);
        gantryStatusService.insertGantryStatus(tblDeviceInfoGantry.getId());
        return toAjax(result);
    }

    @RequiresPermissions("vocational:deviceGantry:edit")
    @Log(title = "门架设备基础信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblGantryInfo tblDeviceInfoGantry)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoGantryService.checkDeviceIdUnique(tblDeviceInfoGantry)))
        {
            return AjaxResult.error("修改门架设备编号'" + tblDeviceInfoGantry.getDeviceId() + "'失败，门架设备编号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoGantryService.checkDeviceNameUnique(tblDeviceInfoGantry)))
        {
            return AjaxResult.error("修改门架设备名称'" + tblDeviceInfoGantry.getDeviceName() + "'失败，门架设备名称已存在");
        }
        return toAjax(tblDeviceInfoGantryService.updateDeviceInfoGantry(tblDeviceInfoGantry));
    }

}
