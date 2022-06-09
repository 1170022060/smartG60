package com.pingok.vocational.controller.roster;

import com.pingok.vocational.domain.roster.TblTractorListRecord;
import com.pingok.vocational.service.roster.ITractorListRecordService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 牵引车信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tractorList")
public class TractorListRecordController extends BaseController {

    @Autowired
    private ITractorListRecordService tractorListRecordService;

    @GetMapping(value="/selectByTime")
    public AjaxResult selectByTime(Date startTime, Date endTime)
    {
        return AjaxResult.success(tractorListRecordService.selectByTime(startTime, endTime));
    }

    @RequiresPermissions("vocational:tractorList:idInfo")
    @Log(title = "牵引车管理", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblTractorListRecord idInfo = tractorListRecordService.selectTractorListById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:tractorList:info")
    @Log(title = "牵引车管理", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "vehPlate",required = false) String vehPlate,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = tractorListRecordService.selectTractorList(vehPlate,status);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:tractorList:add")
    @Log(title = "牵引车管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblTractorListRecord tblTractorListRecord)
    {
        if (UserConstants.NOT_UNIQUE.equals(tractorListRecordService.checkVehPlateUnique(tblTractorListRecord)))
        {
            return AjaxResult.error("新增车辆'" + tblTractorListRecord.getVehPlate() + "'失败，该车已存在");
        }
        return toAjax(tractorListRecordService.insertTractorList(tblTractorListRecord));
    }

    @RequiresPermissions("vocational:tractorList:edit")
    @Log(title = "牵引车管理", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblTractorListRecord tblTractorListRecord)
    {
        if (UserConstants.NOT_UNIQUE.equals(tractorListRecordService.checkVehPlateUnique(tblTractorListRecord)))
        {
            return AjaxResult.error("修改车辆'" + tblTractorListRecord.getVehPlate() + "'失败，该车已存在");
        }
        return toAjax(tractorListRecordService.updateTractorList(tblTractorListRecord));
    }

    @RequiresPermissions("vocational:tractorList:status")
    @Log(title = "牵引车管理", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(tractorListRecordService.updateStatus(id,status));
    }
}
