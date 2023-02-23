package com.pingok.vocational.controller.primaryCar;

import com.pingok.vocational.service.primaryCar.IPrimaryCarService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author lal
 */
@RestController
@RequestMapping("/primaryCar")
public class primaryCarController extends BaseController {

    @Autowired
    private IPrimaryCarService iPrimaryCarService;

    @Log(title = "两客一危车辆信息查询",businessType = BusinessType.OTHER)
    @GetMapping
    public TableDataInfo getPrimaryInfo(@RequestParam(name = "vehPlate",required = false) String vehPlate){
        startPage();
        return getDataTable(iPrimaryCarService.getPrimaryVehInfo(vehPlate));
    }

    @RequiresPermissions("vocational:primaryCar:vehTrail")
    @Log(title = "车辆轨迹查询",businessType = BusinessType.OTHER)
    @GetMapping("/getVehTrail")
    public AjaxResult getVehTrail(@RequestParam(name = "vehPlate") String vehPlate){
        return AjaxResult.success(iPrimaryCarService.getVehTrailInfo(vehPlate));
    }

    @Log(title = "运单查询",businessType = BusinessType.OTHER)
    @GetMapping("/getWayBill")
    public TableDataInfo getWayBillInfo(@RequestParam(name = "vehPlate") String vehPlate){
        startPage();
        return getDataTable(iPrimaryCarService.getWayBillInfo(vehPlate));
    }

    @GetMapping("/getPrimaryGps")
    public TableDataInfo getPrimaryGps(@RequestParam(name = "vehPlate",required = false) String vehPlate){
        startPage();
        return getDataTable(iPrimaryCarService.selectPrimaryGpsInfo(vehPlate));
    }

    @RequiresPermissions("vocational:primaryCar:vehGps")
    @Log(title = "车辆GPS轨迹查询",businessType = BusinessType.OTHER)
    @GetMapping("/getVehGpsList")
    public AjaxResult getVehGpsList(@RequestParam(name = "vehPlate") String vehPlate){
        return AjaxResult.success(iPrimaryCarService.getVehGpsList(vehPlate));
    }

    @GetMapping("/getOwInfo")
    public TableDataInfo getOwInfo(@RequestParam(name = "vehPlate",required = false) String vehPlate,
                                   @RequestParam(name = "checkStartTime",required = false) Date checkStartTime,
                                   @RequestParam(name = "checkEndTime",required = false) Date checkEndTime){
        startPage();
        return getDataTable(iPrimaryCarService.selectOwInfo(vehPlate,checkStartTime,checkEndTime));
    }
}
