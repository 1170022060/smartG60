package com.pingok.vocational.controller.business;

import com.pingok.vocational.domain.business.TblOptInfo;
import com.pingok.vocational.service.business.IOptInfoService;
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
 * 员工信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/optInfo")
public class OptInfoController extends BaseController {

    @Autowired
    private IOptInfoService optInfoService;

    @RequiresPermissions("vocational:optInfo:idInfo")
    @Log(title = "员工信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblOptInfo idInfo = optInfoService.selectOptInfoById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:optInfo:info")
    @Log(title = "员工信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "belongStation",required = false) String belongStation, @RequestParam(name = "belongCenter",required = false) String belongCenter, @RequestParam(name = "optName",required = false) String optName, @RequestParam(name = "optId",required = false) Integer optId, @RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = optInfoService.selectOptInfo(belongStation, belongCenter, optName, optId, status);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:optInfo:add")
    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblOptInfo tblOptInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(optInfoService.checkOptIdUnique(tblOptInfo)))
        {
            return AjaxResult.error("新增员工工号'" + tblOptInfo.getOptId() + "'失败，员工工号已存在");
        }
        return toAjax(optInfoService.insertOptInfo(tblOptInfo));
    }

    @RequiresPermissions("vocational:optInfo:edit")
    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblOptInfo tblOptInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(optInfoService.checkOptIdUnique(tblOptInfo)))
        {
            return AjaxResult.error("修改员工工号'" + tblOptInfo.getOptId() + "'失败，员工工号已存在");
        }
        return toAjax(optInfoService.updateOptInfo(tblOptInfo));
    }

    @RequiresPermissions("vocational:optInfo:status")
    @Log(title = "员工信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(optInfoService.updateStatus(id,status));
    }

    @GetMapping(value="/issue")
    public AjaxResult issue()
    {
        optInfoService.issueOptInfo();
        return AjaxResult.success();
    }
}
