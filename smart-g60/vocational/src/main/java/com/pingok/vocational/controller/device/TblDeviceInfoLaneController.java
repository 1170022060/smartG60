package com.pingok.vocational.controller.device;

import com.pingok.vocational.domain.device.TblDeviceInfoLane;
import com.pingok.vocational.domain.device.vo.LaneEnum;
import com.pingok.vocational.service.device.TblDeviceInfoLaneService;
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
 * 车道设备信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceLane")
public class TblDeviceInfoLaneController extends BaseController {
    @Autowired
    private TblDeviceInfoLaneService tblDeviceInfoLaneService;

    @RequiresPermissions("vocational:deviceLane:idInfo")
    @Log(title = "车道设备信息-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblDeviceInfoLane idInfo = tblDeviceInfoLaneService.selectDeviceInfoLaneById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:deviceLane:info")
    @Log(title = "车道设备信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "laneHex") String laneHex,@RequestParam(name = "deviceCategory",required = false) Long deviceCategory, @RequestParam(name = "status",required = false) Integer status, @RequestParam(name = "laneBelong",required = false) String laneBelong, @RequestParam(name = "deviceId",required = false) String deviceId, @RequestParam(name = "userSide",required = false) Long userSide, @RequestParam(name = "managementSide",required = false) Long managementSide, @RequestParam(name = "serviceLife",required = false) Integer serviceLife, @RequestParam(name = "deviceName",required = false) String deviceName)
    {
        startPage();
        List<Map> info = tblDeviceInfoLaneService.selectDeviceInfoLane(laneHex, deviceCategory,  status,  laneBelong,  deviceId,  userSide,  managementSide, serviceLife,deviceName);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:deviceLane:add")
    @Log(title = "车道设备基础信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody LaneEnum laneEnum)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoLaneService.checkDeviceIdUnique(laneEnum.getTblDeviceInfoLane())))
        {
            return AjaxResult.error("新增车道设备编号'" + laneEnum.getTblDeviceInfoLane().getDeviceId() + "'失败，车道设备编号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoLaneService.checkDeviceNameUnique(laneEnum.getTblDeviceInfoLane())))
        {
            return AjaxResult.error("新增车道设备名称'" + laneEnum.getTblDeviceInfoLane().getDeviceName() + "'失败，车道设备名称已存在");
        }
        return toAjax(tblDeviceInfoLaneService.insertDeviceInfoLane(laneEnum));
    }

    @RequiresPermissions("vocational:deviceLane:edit")
    @Log(title = "车道设备基础信息", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult edit(@Validated @RequestBody TblDeviceInfoLane tblDeviceInfoLane)
    {
        if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoLaneService.checkDeviceIdUnique(tblDeviceInfoLane)))
        {
            return AjaxResult.error("修改车道设备编号'" + tblDeviceInfoLane.getDeviceId() + "'失败，车道设备编号已存在");
        }
        else if (UserConstants.NOT_UNIQUE.equals(tblDeviceInfoLaneService.checkDeviceNameUnique(tblDeviceInfoLane)))
        {
            return AjaxResult.error("修改车道设备名称'" + tblDeviceInfoLane.getDeviceName() + "'失败，车道设备名称已存在");
        }
        return toAjax(tblDeviceInfoLaneService.updateDeviceInfoLane(tblDeviceInfoLane));
    }

}
