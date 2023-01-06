package com.pingok.vocational.controller.maintenance;

import com.pingok.vocational.domain.maintenance.TblMaintainCarGps;
import com.pingok.vocational.service.maintenance.IMaintainCarGpsService;
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
 * 养护车辆GPS信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/maintainCar")
public class MaintainCarGpsController extends BaseController {
    @Autowired
    private IMaintainCarGpsService maintainCarGpsService;

//    @RequiresPermissions("vocational:maintainCar:all")
    @Log(title = "养护车辆GPS信息-全部查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/all")
    public AjaxResult all()
    {
        List<TblMaintainCarGps> info = maintainCarGpsService.selectAll();
        return AjaxResult.success(info);
    }

    @Log(title = "养护车辆信息-分页查询",businessType = BusinessType.OTHER)
    @GetMapping
    public TableDataInfo getInfo(@RequestParam(name = "vehPlate",required = false) String vehPlate){
        startPage();
        List<Map> info = maintainCarGpsService.selectInfo(vehPlate);
        return getDataTable(info);
    }
}
