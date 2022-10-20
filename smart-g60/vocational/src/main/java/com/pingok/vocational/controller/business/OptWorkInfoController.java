package com.pingok.vocational.controller.business;

import com.pingok.vocational.domain.business.TblOptWorkInfo;
import com.pingok.vocational.service.business.IOptWorkInfoService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 操作员工班信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/optWork")
public class OptWorkInfoController extends BaseController {

    @Autowired
    private IOptWorkInfoService optWorkInfoService;

//    @RequiresPermissions("vocational:optWork:info")
//    @Log(title = "操作员工班信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate,@RequestParam(name = "stationId",required = false) String stationId,@RequestParam(name = "optName",required = false) String optName,@RequestParam(name = "shift",required = false) Integer shift,@RequestParam(name = "optId",required = false) Integer optId)
    {
        startPage();
        List<Map> info = optWorkInfoService.selectOptWorkInfo(startDate,  endDate,  stationId,  optName,  shift,optId);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:optWork:add")
    @Log(title = "工班信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblOptWorkInfo tblOptWorkInfo)
    {
        return toAjax(optWorkInfoService.insertOptWorkInfo(tblOptWorkInfo));
    }

    @RequiresPermissions("vocational:optWork:status")
    @Log(title = "工班信息-重置", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id)
    {
        return toAjax(optWorkInfoService.updateOptWorkInfo(id));
    }

    @GetMapping(value="/issue")
    public AjaxResult issue()
    {
        optWorkInfoService.issueOptWorkInfo();
        return AjaxResult.success();
    }
}
