package com.pingok.vocational.controller.emergency;

import com.pingok.vocational.domain.emergency.TblEventPaln;
import com.pingok.vocational.service.emergency.IEventPalnService;
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
 * 应急预案管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/eventPaln")
public class EventPalnController extends BaseController {

    @Autowired
    private IEventPalnService iEventPalnService;

    @GetMapping("/deviceList")
    public AjaxResult deviceList(Long eventId, Long planId, Integer type) {
        return AjaxResult.success(iEventPalnService.deviceList(eventId, planId, type));
    }

    /**
     * 获取应急预案列表
     */
//    @RequiresPermissions("vocational:eventPaln:findByEventType")
//    @Log(title = "应急预案管理", businessType = BusinessType.UPDATE)
    @GetMapping("/findByEventType")
    public AjaxResult findByEventType(@RequestParam Long id) {
        return AjaxResult.success(iEventPalnService.findByEventType(id));
    }

    /**
     * 停用/启用
     */
    @RequiresPermissions("vocational:eventPaln:disableOrEnable")
    @Log(title = "应急预案管理", businessType = BusinessType.UPDATE)
    @PutMapping("/disableOrEnable")
    public AjaxResult disableOrEnable(@RequestParam Long id, @RequestParam Integer status) {
        iEventPalnService.disableOrEnable(id, status);
        return AjaxResult.success();
    }

    /**
     * 根据id查询应急预案
     */
    @GetMapping
    public AjaxResult findById(@RequestParam Long id) {
        return AjaxResult.success(iEventPalnService.findById(id));
    }

    /**
     * 获取应急预案列表
     */
    @GetMapping("/list")
    public TableDataInfo list(String planTitle) {
        startPage();
        List<Map> list = iEventPalnService.selectEventPalnList(planTitle);
        return getDataTable(list);
    }

    /**
     * 新增应急预案
     */
    @RequiresPermissions("vocational:eventPaln:add")
    @Log(title = "应急预案管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblEventPaln tblEventPaln) {
        if (UserConstants.NOT_UNIQUE.equals(iEventPalnService.checkEventPalnNameUnique(tblEventPaln))) {
            return AjaxResult.error("新增应急预案'" + tblEventPaln.getPlanTitle() + "'失败，预案标题已存在");
        }
        return toAjax(iEventPalnService.insert(tblEventPaln));
    }

    /**
     * 修改用户
     */
    @RequiresPermissions("vocational:eventPaln:edit")
    @Log(title = "应急预案管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblEventPaln tblEventPaln) {
        if (UserConstants.NOT_UNIQUE.equals(iEventPalnService.checkEventPalnNameUnique(tblEventPaln))) {
            return AjaxResult.error("修改应急预案'" + tblEventPaln.getPlanTitle() + "'失败，预案标题已存在");
        }
        return toAjax(iEventPalnService.update(tblEventPaln));
    }

    @GetMapping("/selectGroup")
    public AjaxResult getGroup(@RequestParam(name = "suppliesType") Integer suppliesType){
        return AjaxResult.success(iEventPalnService.selectPlanGroup(suppliesType));
    }
}
