package com.pingok.vocational.controller.device;

import com.pingok.vocational.domain.device.TblDeviceCategory;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
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
 * 设备类目
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceCategory")
public class TblDeviceCategoryController extends BaseController {

    @Autowired
    private TblDeviceCategoryService tblDeviceCategoryService;

    @RequiresPermissions("vocational:deviceCategory:idInfo")
    @Log(title = "设备类目-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblDeviceCategory idInfo = tblDeviceCategoryService.selectCategoryById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:deviceCategory:info")
    @Log(title = "设备类目-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "categoryName",required = false) String categoryName,@RequestParam(name = "parentCategory",required = false) Long parentCategory,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = tblDeviceCategoryService.selectDeviceCategory(categoryName,parentCategory,status);
        return getDataTable(info);
    }

    @Log(title = "设备类目-树形查询", businessType = BusinessType.OTHER)
    @GetMapping(value = "/tree")
    public AjaxResult tree()
    {
        List<TblDeviceCategory> menus = tblDeviceCategoryService.selectAll();
        return AjaxResult.success(tblDeviceCategoryService.buildMenuTreeSelect(menus));
    }

    @RequiresPermissions("vocational:deviceCategory:add")
    @Log(title = "设备类目信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblDeviceCategory tblDeviceCategory)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceCategoryService.checkCategoryNameUnique(tblDeviceCategory)))
        {
            return AjaxResult.error("新增类目名'" + tblDeviceCategory.getCategoryName() + "'失败，类目名已存在");
        }
        return toAjax(tblDeviceCategoryService.insertDeviceCategory(tblDeviceCategory));
    }

    @RequiresPermissions("vocational:deviceCategory:edit")
    @Log(title = "设备类目信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblDeviceCategory tblDeviceCategory)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceCategoryService.checkCategoryNameUnique(tblDeviceCategory)))
        {
            return AjaxResult.error("修改类目名'" + tblDeviceCategory.getCategoryName() + "'失败，类目名已存在");
        }
        return toAjax(tblDeviceCategoryService.updateDeviceCategory(tblDeviceCategory));
    }

    @RequiresPermissions("vocational:deviceCategory:status")
    @Log(title = "设备类目信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(tblDeviceCategoryService.updateStatus(id,status));
    }

}
