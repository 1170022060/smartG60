package com.pingok.vocational.controller.dropdownlist;

import com.pingok.vocational.domain.baseinfo.vo.StationInfo;
import com.pingok.vocational.domain.field.vo.FieldVo;
import com.pingok.vocational.mapper.emergency.TblEmergencyGroupMapper;
import com.pingok.vocational.service.baseinfo.IBaseStationInfoService;
import com.pingok.vocational.service.baseinfo.ILaneInfoService;
import com.pingok.vocational.service.device.IGantryInfoService;
import com.pingok.vocational.service.device.TblDeviceInfoService;
import com.pingok.vocational.service.emergency.TblEmergencyGroupService;
import com.pingok.vocational.service.emergency.TblEmergencySuppliesService;
import com.pingok.vocational.service.field.TblFieldInfoService;
import com.pingok.vocational.service.project.IProjectInfoService;
import com.pingok.vocational.service.release.IReleasePresetService;
import com.ruoyi.common.core.web.controller.BaseController;
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

    @RequiresPermissions("vocational:dropDownList:field")
    @Log(title = "所属场地下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/field")
    public TableDataInfo field()
    {
        startPage();
        List<FieldVo> info = tblFieldInfoService.selectFieldTypeName();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:station")
    @Log(title = "站编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/station")
    public TableDataInfo station()
    {
        startPage();
        List<Map> info = baseStationInfoService.selectStationInfo();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:lane")
    @Log(title = "车道编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/lane")
    public TableDataInfo lane()
    {
        startPage();
        List<Map> info = laneInfoService.selectLaneInfo();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:stationLane")
    @Log(title = "站及对应车道编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/stationLane")
    public TableDataInfo stationLane()
    {
        startPage();
        List<StationInfo> info = baseStationInfoService.selectStationLaneInfo();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:stationCenter")
    @Log(title = "站及中心编码下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/stationCenter")
    public TableDataInfo stationCenter()
    {
        startPage();
        List<Map> info = baseStationInfoService.selectStationCenterInfo();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:deptUser")
    @Log(title = "查询部门-人员下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/deptUser")
    public TableDataInfo deptUser()
    {
        startPage();
        List<Map> info = tblEmergencyGroupService.selectDeptUser();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:project")
    @Log(title = "项目信息-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/project")
    public TableDataInfo project()
    {
        startPage();
        List<Map> info = projectInfoService.selectProjectName();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:device")
    @Log(title = "设备信息-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/device")
    public TableDataInfo device()
    {
        startPage();
        List<Map> info = tblDeviceInfoService.selectDeviceName();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:suppliesName")
    @Log(title = "资源名称-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/suppliesName")
    public TableDataInfo suppliesName(@RequestParam(name = "suppliesType") Integer suppliesType)
    {
        startPage();
        List<Map> info = tblEmergencySuppliesService.selectEmergencyName(suppliesType);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:board")
    @Log(title = "情报板/限速板-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/board")
    public TableDataInfo board(@RequestParam(name = "type") Integer type)
    {
        startPage();
        List<Map> info = releasePresetService.selectDeviceBoard(type);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:deviceGantry")
    @Log(title = "门架设备-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/deviceGantry")
    public TableDataInfo board()
    {
        startPage();
        List<Map> info = gantryInfoService.selectGantryName();
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:dropDownList:preset")
    @Log(title = "信息发布预设-下拉列表查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/preset")
    public TableDataInfo preset(@RequestParam(name = "infoType") Integer infoType)
    {
        startPage();
        List<Map> info = releasePresetService.selectReleaseInfo(infoType);
        return getDataTable(info);
    }
}
