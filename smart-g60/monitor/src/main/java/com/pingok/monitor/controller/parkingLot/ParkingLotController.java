package com.pingok.monitor.controller.parkingLot;

import com.pingok.monitor.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 停车场 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/parkingLot")
public class ParkingLotController extends BaseController {

    @Autowired
    private IParkingLotService iParkingLotService;

    @GetMapping("/flowStatistics")
    public AjaxResult flowStatistics() {
        return AjaxResult.success(iParkingLotService.flowStatistics());
    }

    @GetMapping
    public AjaxResult findByFieldNum(@RequestParam String fieldNum) {
        return AjaxResult.success(iParkingLotService.findByFieldNum(fieldNum));
    }
}
