package com.pingok.vocational.controller.baseinfo;

import com.pingok.vocational.domain.baseinfo.TblLaneInfo;
import com.pingok.vocational.service.baseinfo.ILaneInfoService;
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
 * 车道基础信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/laneInfo")
public class LaneInfoController extends BaseController {

    @Autowired
    private ILaneInfoService laneInfoService;

    @RequiresPermissions("vocational:laneInfo:idInfo")
    @Log(title = "车道基础信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblLaneInfo idInfo = laneInfoService.selectLaneById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:laneInfo:info")
    @Log(title = "车道基础信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "stationId",required = false) String stationId,@RequestParam(name = "status",required = false) Integer status)
    {
        startPage();
        List<Map> info = laneInfoService.selectLane(stationId,status);
        return getDataTable(info);
    }


    @RequiresPermissions("vocational:laneInfo:add")
    @Log(title = "车道基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblLaneInfo tblLaneInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(laneInfoService.checkLaneNameUnique(tblLaneInfo)))
        {
            return AjaxResult.error("新增车道名'" + tblLaneInfo.getLaneName() + "'失败，车道名已存在");
        }
        return toAjax(laneInfoService.insertLane(tblLaneInfo));
    }

    @RequiresPermissions("vocational:laneInfo:edit")
    @Log(title = "车道基础信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblLaneInfo tblLaneInfo)
    {
        if (UserConstants.NOT_UNIQUE.equals(laneInfoService.checkLaneNameUnique(tblLaneInfo)))
        {
            return AjaxResult.error("修改车道名'" + tblLaneInfo.getLaneName() + "'失败，车道名已存在");
        }
        return toAjax(laneInfoService.updateLane(tblLaneInfo));
    }

    @RequiresPermissions("vocational:laneInfo:status")
    @Log(title = "车道基础信息-状态改变", businessType = BusinessType.UPDATE)
    @PutMapping("/status")
    public AjaxResult status(@RequestParam(name = "id") Long id,@RequestParam(name = "status") Integer status)
    {
        return toAjax(laneInfoService.updateStatus(id,status));
    }
}
