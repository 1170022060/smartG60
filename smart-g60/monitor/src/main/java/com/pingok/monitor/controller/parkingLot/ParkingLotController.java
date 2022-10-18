package com.pingok.monitor.controller.parkingLot;

import com.pingok.monitor.service.parkingLot.IParkingLotService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

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

    @GetMapping("/getTimeOutVeh")
    public TableDataInfo list(Long id){
        startPage();
        List<Map> info = iParkingLotService.getTimeOutVeh(id);
        return getDataTable(info);
    }
}
