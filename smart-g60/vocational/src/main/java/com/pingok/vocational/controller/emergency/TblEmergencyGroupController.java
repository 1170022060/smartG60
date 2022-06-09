package com.pingok.vocational.controller.emergency;

import com.pingok.vocational.domain.emergency.TblEmergencyGroup;
import com.pingok.vocational.service.emergency.TblEmergencyGroupService;
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
 * 应急小组信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/emergencyGroup")
public class TblEmergencyGroupController extends BaseController {

    @Autowired
    private TblEmergencyGroupService tblEmergencyGroupService;

    @RequiresPermissions("vocational:emergencyGroup:idInfo")
    @Log(title = "应急小组信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblEmergencyGroup idInfo = tblEmergencyGroupService.selectEmergencyGroupById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:emergencyGroup:info")
    @Log(title = "应急小组信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "groupName",required = false) String groupName, @RequestParam(name = "groupLeader",required = false) Long groupLeader,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = tblEmergencyGroupService.selectEmergencyGroup(groupName,groupLeader,status);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:emergencyGroup:add")
    @Log(title = "应急小组信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblEmergencyGroup tblEmergencyGroup)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencyGroupService.checkGroupNameUnique(tblEmergencyGroup)))
        {
            return AjaxResult.error("新增组名'" + tblEmergencyGroup.getGroupName() + "'失败，组名已存在");
        }
        return toAjax(tblEmergencyGroupService.insertEmergencyGroup(tblEmergencyGroup));
    }

    @RequiresPermissions("vocational:emergencyGroup:edit")
    @Log(title = "应急小组信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblEmergencyGroup tblEmergencyGroup)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblEmergencyGroupService.checkGroupNameUnique(tblEmergencyGroup)))
        {
            return AjaxResult.error("修改组名'" + tblEmergencyGroup.getGroupName() + "'失败，组名已存在");
        }
        return toAjax(tblEmergencyGroupService.updateEmergencyGroup(tblEmergencyGroup));
    }

    @RequiresPermissions("vocational:emergencyGroup:status")
    @Log(title = "应急小组信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(tblEmergencyGroupService.updateStatus(id,status));
    }
}
