package com.pingok.vocational.controller.dropdownlist;

import com.pingok.vocational.domain.baseinfo.vo.StationInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import com.pingok.vocational.mapper.emergency.TblEmergencyGroupMapper;
import com.pingok.vocational.service.baseinfo.IBaseStationInfoService;
import com.pingok.vocational.service.baseinfo.ILaneInfoService;
import com.pingok.vocational.service.device.IGantryInfoService;
import com.pingok.vocational.service.device.TblDeviceCategoryService;
import com.pingok.vocational.service.device.TblDeviceInfoService;
import com.pingok.vocational.service.emergency.TblEmergencyGroupService;
import com.pingok.vocational.service.emergency.TblEmergencySuppliesService;
import com.pingok.vocational.service.field.TblFieldInfoService;
import com.pingok.vocational.service.project.IProjectInfoService;
import com.pingok.vocational.service.release.IReleasePresetService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 下拉列表接口
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/dropDownList")
public class DropDownListController extends BaseController {
    @Autowired
    private TblFieldInfoService tblFieldInfoService;
    @Autowired
    private IBaseStationInfoService baseStationInfoService;
    @Autowired
    private ILaneInfoService laneInfoService;
    @Autowired
    private TblEmergencyGroupService tblEmergencyGroupService;
    @Autowired
    private IProjectInfoService projectInfoService;
    @Autowired
    private TblDeviceInfoService tblDeviceInfoService;
    @Autowired
    private TblEmergencySuppliesService tblEmergencySuppliesService;
    @Autowired
    private IReleasePresetService releasePresetService;
    @Autowired
    private IGantryInfoService gantryInfoService;
    @Autowired
    private TblDeviceCategoryService tblDeviceCategoryService;

    @RequiresPermissions("vocational:dropDownList:field")
    @Log(title = "所属场地下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/field")
    public AjaxResult field()
    {
        List<FieldVo> info = tblFieldInfoService.selectFieldTypeName();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:station")
    @Log(title = "站编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/station")
    public AjaxResult station()
    {
        List<Map> info = baseStationInfoService.selectStationInfo();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:lane")
    @Log(title = "车道编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/lane")
    public AjaxResult lane()
    {
        List<Map> info = laneInfoService.selectLaneInfo();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:stationLane")
    @Log(title = "站及对应车道编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/stationLane")
    public AjaxResult stationLane()
    {
        List<StationInfo> info = baseStationInfoService.selectStationLaneInfo();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:stationCenter")
    @Log(title = "站及中心编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/stationCenter")
    public AjaxResult stationCenter()
    {
        List<Map> info = baseStationInfoService.selectStationCenterInfo();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:deptUser")
    @Log(title = "查询部门-人员下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/deptUser")
    public AjaxResult deptUser()
    {
        List<Map> info = tblEmergencyGroupService.selectDeptUser();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:project")
    @Log(title = "项目信息-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/project")
    public AjaxResult project()
    {
        List<Map> info = projectInfoService.selectProjectName();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:device")
    @Log(title = "设备信息-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/device")
    public AjaxResult device()
    {
        List<Map> info = tblDeviceInfoService.selectDeviceName();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:suppliesName")
    @Log(title = "资源名称-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/suppliesName")
    public AjaxResult suppliesName(@RequestParam(name = "suppliesType") Integer suppliesType)
    {
        List<Map> info = tblEmergencySuppliesService.selectEmergencyName(suppliesType);
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:board")
    @Log(title = "情报板/限速板-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/board")
    public AjaxResult board(@RequestParam(name = "type") Integer type)
    {
        List<Map> info = releasePresetService.selectDeviceBoard(type);
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:deviceGantry")
    @Log(title = "门架设备-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/deviceGantry")
    public AjaxResult deviceGantry()
    {
        List<Map> info = gantryInfoService.selectGantryName();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:preset")
    @Log(title = "信息发布预设-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/preset")
    public AjaxResult preset(@RequestParam(name = "infoType") Integer infoType)
    {
        List<Map> info = releasePresetService.selectReleaseInfo(infoType);
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:dropDownList:post")
    @Log(title = "岗位下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/post")
    public AjaxResult preset()
    {
        List<Map> info = tblDeviceCategoryService.selectPost();
        return AjaxResult.success(info);
    }
}
