package com.pingok.vocational.controller.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblBaseStationInfo;
import com.pingok.vocational.service.baseinfo.IBaseStationInfoService;
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
 * 收费站基础信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/baseStation")
public class BaseStationInfoController extends BaseController {

    @Autowired
    private IBaseStationInfoService baseStationInfoService;

    @RequiresPermissions("vocational:baseStation:findByNetWorkAndStationId")
    @Log(title = "收费站基础信息", businessType = BusinessType.OTHER)
    @GetMapping(value = "/findByNetWorkAndStationId")
    public AjaxResult findByNetWorkAndStationId(@RequestParam(name = "netWork") String netWork, @RequestParam(name = "stationId") String stationId) {
        return AjaxResult.success(baseStationInfoService.findByNetWorkAndStationId(netWork, stationId));
    }

    @RequiresPermissions("vocational:baseStation:idInfo")
    @Log(title = "收费站基础信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value = "/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id) {
        TblBaseStationInfo idInfo = baseStationInfoService.selectBaseStationById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:baseStation:info")
    @Log(title = "收费站基础信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "stationName", required = false) String stationName) {
        startPage();
        List<Map> info = baseStationInfoService.selectBaseStation(stationName);
        return getDataTable(info);
    }


    @RequiresPermissions("vocational:baseStation:add")
    @Log(title = "收费站基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblBaseStationInfo tblBaseStationInfo) {
        return toAjax(baseStationInfoService.insertBaseStation(tblBaseStationInfo));
    }

    @RequiresPermissions("vocational:baseStation:edit")
    @Log(title = "收费站基础信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblBaseStationInfo tblReleasePreset) {

        return toAjax(baseStationInfoService.updateBaseStation(tblReleasePreset));
    }
}
