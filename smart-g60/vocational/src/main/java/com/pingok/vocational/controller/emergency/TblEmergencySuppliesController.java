package com.pingok.vocational.controller.emergency;

import com.pingok.vocational.domain.emergency.TblEmergencySupplies;
import com.pingok.vocational.service.emergency.TblEmergencySuppliesService;
import com.ruoyi.common.core.constant.UserConstants;
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
 * 应急资源信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/emergencySupplies")
public class TblEmergencySuppliesController extends BaseController {
    @Autowired
    private TblEmergencySuppliesService tblEmergencySuppliesService;

    @RequiresPermissions("vocational:emergencySupplies:idInfo")
    @Log(title = "应急资源信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblEmergencySupplies idInfo = tblEmergencySuppliesService.selectEmergencySuppliesById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:emergencySupplies:info")
    @Log(title = "应急资源信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "suppliesType") Integer suppliesType,@RequestParam(name = "suppliesName",required = false) String suppliesName,@RequestParam(name = "vehPlate",required = false) String vehPlate,@RequestParam(name = "expertName",required = false) String expertName,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = tblEmergencySuppliesService.selectSupplies(suppliesType,suppliesName,vehPlate,expertName,status);
        return getDataTable(info);
    }


    @RequiresPermissions("vocational:emergencySupplies:add")
    @Log(title = "应急资源信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblEmergencySupplies tblEmergencySupplies)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkSuppliesNameUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("新增物资名称'" + tblEmergencySupplies.getSuppliesName() + "'失败，该物资名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkVehPlateUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("新增车辆'" + tblEmergencySupplies.getVehPlate() + "'失败，该车已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkExpertNameUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("新增专家'" + tblEmergencySupplies.getExpertName() + "'失败，专家已存在");
        }
        return toAjax(tblEmergencySuppliesService.insertEmergencySupplies(tblEmergencySupplies));
    }

    @RequiresPermissions("vocational:emergencySupplies:edit")
    @Log(title = "应急资源信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblEmergencySupplies tblEmergencySupplies)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkSuppliesNameUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("修改物资名称'" + tblEmergencySupplies.getSuppliesName() + "'失败，该物资名称已存在");
        }
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkVehPlateUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("修改车辆'" + tblEmergencySupplies.getVehPlate() + "'失败，该车已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblEmergencySuppliesService.checkExpertNameUnique(tblEmergencySupplies)))
        {
            return AjaxResult.error("修改专家'" + tblEmergencySupplies.getExpertName() + "'失败，专家已存在");
        }
        return toAjax(tblEmergencySuppliesService.updateEmergencySupplies(tblEmergencySupplies));
    }

    @RequiresPermissions("vocational:emergencySupplies:status")
    @Log(title = "应急资源信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(tblEmergencySuppliesService.updateStatus(id,status));
    }

}
