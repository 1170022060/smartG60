package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequiresPermissions("vocational:parkingLot:statistics")
    @Log(title = "停车场管理", businessType = BusinessType.OTHER)
    @GetMapping("/statistics")
    public AjaxResult driveAway(@RequestParam(name = "date") Date date) {
        return AjaxResult.success(iParkingLotService.trafficChange(date));
    }
}
