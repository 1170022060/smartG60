package com.pingok.vocational.controller.roster;

import com.pingok.vocational.service.roster.IVehicleRecordService;
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
 * 车辆通行记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/vehicleRecord")
public class VehicleRecordController extends BaseController {

    @Autowired
    private IVehicleRecordService tblVehicleRecordService;

    @RequiresPermissions("vocational:vehicleRecord:info")
    @Log(title = "车辆通行记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public TableDataInfo info(@RequestParam(name = "vehPlate",required = false) String vehPlate)
    {
        startPage();
        List<Map> info = tblVehicleRecordService.selectVehicleRecord(vehPlate);
        return getDataTable(info);
    }
}
