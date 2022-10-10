package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

/**
 * 停车场管理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/parkingLot")
public class ParkingLotController extends BaseController {
    @Autowired
    private IParkingLotService iParkingLotService;

    @RequiresPermissions("vocational:parkingLot:driveAway")
    @Log(title = "停车场管理", businessType = BusinessType.UPDATE)
    @PutMapping("/driveAway")
    public AjaxResult driveAway(@RequestParam Long id) {
        iParkingLotService.driveAway(id);
        return AjaxResult.success();
    }

    @RequiresPermissions("vocational:parkingLot:updateSurplus")
    @Log(title = "停车场管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateSurplus")
    public AjaxResult updateSurplus(@RequestParam Long id, @RequestParam Integer surplus) {
        if (surplus < 0) {
            return AjaxResult.error("剩余车位数不能小于0");
        }
        iParkingLotService.updateSurplus(id, surplus);
        return AjaxResult.success();
    }

    @Log(title = "车流统计", businessType = BusinessType.OTHER)
    @GetMapping("/statistics")
    public AjaxResult statistics(@RequestParam(name = "date") Date date) {
        return AjaxResult.success(iParkingLotService.trafficChange(date));
    }

    @Log(title = "停车场车位统计", businessType = BusinessType.OTHER)
    @GetMapping("/place")
    public AjaxResult place() {
        return AjaxResult.success(iParkingLotService.parkingPlace());
    }

    @Log(title = "客流量统计", businessType = BusinessType.OTHER)
    @GetMapping("/passenger")
    public AjaxResult passenger(@RequestParam(name = "date") Date date) throws ParseException {
        return AjaxResult.success(iParkingLotService.passengerFlow(date));
    }

    @Log(title = "车位统计", businessType = BusinessType.OTHER)
    @GetMapping("/park")
    public AjaxResult park(@RequestParam(name = "fieldNum",required = false) String fieldNum,@RequestParam(name = "regionName",required = false) String regionName){
        return AjaxResult.success(iParkingLotService.parkMonitor(fieldNum,regionName));
    }

    @Log(title = "超时车辆", businessType = BusinessType.OTHER)
    @GetMapping("/overtime")
    public TableDataInfo overtime(@RequestParam(name = "fieldNum",required = false) String fieldNum, @RequestParam(name = "regionName",required = false) String regionName){
        startPage();
        return getDataTable(iParkingLotService.overtimeInfo(fieldNum,regionName));
    }

    @Log(title = "车流统计(页面)", businessType = BusinessType.OTHER)
    @GetMapping("/traffic")
    public TableDataInfo traffic(@RequestParam(name = "fieldNum",required = false) String fieldNum, @RequestParam(name = "vehType",required = false) Integer vehType,@RequestParam(name = "startDate",required = false) Date startDate,@RequestParam(name = "endDate",required = false) Date endDate, @RequestParam(name = "statisticsType") Integer statisticsType){
        startPage();
        return getDataTable(iParkingLotService.traffic(fieldNum,vehType,startDate,endDate,statisticsType));
    }

    @Log(title = "人流统计(页面)", businessType = BusinessType.OTHER)
    @GetMapping("/humanFlow")
    public TableDataInfo humanFlow(@RequestParam(name = "fieldNum",required = false) String fieldNum, @RequestParam(name = "areaId",required = false) Integer areaId,@RequestParam(name = "startDate",required = false) Date startDate,@RequestParam(name = "endDate",required = false) Date endDate, @RequestParam(name = "statisticsType") Integer statisticsType){
        startPage();
        return getDataTable(iParkingLotService.humanFlow(fieldNum,areaId,startDate,endDate,statisticsType));
    }
}
