package com.pingok.vocational.controller.software;

import com.pingok.vocational.domain.software.TblSoftwareUpdate;
import com.pingok.vocational.service.software.ISoftwareUpdateService;
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
 * 软件更新信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/softwareUpdate")
public class SoftwareUpdateController extends BaseController {

    @Autowired
    private ISoftwareUpdateService softwareUpdateService;

    @RequiresPermissions("vocational:softwareUpdate:idInfo")
    @Log(title = "软件更新信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblSoftwareUpdate idInfo = softwareUpdateService.selectSoftwareUpdateById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:softwareUpdate:info")
    @Log(title = "软件更新信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "name",required = false) String name,@RequestParam(name = "softwareType",required = false) Integer softwareType,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = softwareUpdateService.selectSoftwareUpdate(name, softwareType, status);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:softwareUpdate:add")
    @Log(title = "软件更新信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblSoftwareUpdate tblSoftwareUpdate)
    {
        return toAjax(softwareUpdateService.insertSoftwareUpdate(tblSoftwareUpdate));
    }

    @RequiresPermissions("vocational:softwareUpdate:edit")
    @Log(title = "软件更新信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblSoftwareUpdate tblSoftwareUpdate)
    {
        return toAjax(softwareUpdateService.updateSoftwareUpdate(tblSoftwareUpdate));
    }

    @RequiresPermissions("vocational:softwareUpdate:published")
    @Log(title = "软件更新信息-发布", businessType = BusinessType.UPDATE)
    @PutMapping("/published")
    public AjaxResult published(@RequestParam(name = "id") Long id)
    {
        return toAjax(softwareUpdateService.updatePublished(id));
    }

    @RequiresPermissions("vocational:softwareUpdate:discard")
    @Log(title = "软件更新信息-下架", businessType = BusinessType.UPDATE)
    @PutMapping("/discard")
    public AjaxResult discard(@RequestParam(name = "id") Long id)
    {
        return toAjax(softwareUpdateService.updateDiscard(id));
    }
}
