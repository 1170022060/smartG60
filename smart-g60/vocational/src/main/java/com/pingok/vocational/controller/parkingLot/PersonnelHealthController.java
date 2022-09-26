package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblPersonnelHealth;
import com.pingok.vocational.service.parkingLot.IPersonnelHealthService;
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
 * 人员健康信息
 *
 * @author xia
 */
@RestController
@RequestMapping("/personnelHealth")
public class PersonnelHealthController extends BaseController {

    @Autowired
    private IPersonnelHealthService iPersonnelHealthService;

    @Log(title = "人员健康信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblPersonnelHealth idInfo = iPersonnelHealthService.selectPersonnelHealthById(id);
        return AjaxResult.success(idInfo);
    }

    @Log(title = "人员健康信息管理-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "name",required = false) String name, @RequestParam(name = "fieldId",required = false) Long fieldId, @RequestParam(name = "date",required = false) Date date)
    {
        startPage();
        List<Map> info = iPersonnelHealthService.selectPersonnelHealth(name, fieldId, date);
        return getDataTable(info);
    }

    @Log(title = "人员健康信息统计-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/statistics")
    public TableDataInfo statistics(@RequestParam(name = "type",required = false) Integer type, @RequestParam(name = "fieldId",required = false) Long fieldId, @RequestParam(name = "date",required = false) Date date)
    {
        startPage();
        List<Map> info = iPersonnelHealthService.selectHealthStatistics(type, fieldId, date);
        return getDataTable(info);
    }

    @Log(title = "人员健康信息监控", businessType = BusinessType.OTHER)
    @GetMapping(value="/monitor")
    public AjaxResult monitor(@RequestParam(name = "date") Date date)
    {
        List<Map> info = iPersonnelHealthService.selectHealthMonitor(date);
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:personnelHealth:add")
    @Log(title = "人员健康信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblPersonnelHealth tblPersonnelHealth)
    {
        return toAjax(iPersonnelHealthService.insertPersonnelHealth(tblPersonnelHealth));
    }

    @RequiresPermissions("vocational:personnelHealth:edit")
    @Log(title = "人员健康信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblPersonnelHealth tblPersonnelHealth)
    {
        return toAjax(iPersonnelHealthService.updatePersonnelHealth(tblPersonnelHealth));
    }
}
