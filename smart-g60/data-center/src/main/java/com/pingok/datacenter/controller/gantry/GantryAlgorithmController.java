package com.pingok.datacenter.controller.gantry;

import com.pingok.datacenter.service.gantry.IGantryAlgorithm;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/gantryAlgorithm")
public class GantryAlgorithmController extends BaseController {

    @Autowired
    private IGantryAlgorithm gantryAlgorithm;

    @GetMapping("/info")
    public AjaxResult info(@RequestParam(name = "startTime",required = false) String startTime, @RequestParam(name = "endTime",required = false) String endTime)
    {
        List<Map> info = gantryAlgorithm.selectGantryAlgorithm(startTime , endTime);
        return AjaxResult.success(info);
    }

    @GetMapping("/gantryList")
    public AjaxResult gantryList(@RequestParam(name = "direction",required = false) Integer direction)
    {
        List<Map> info = gantryAlgorithm.selectGantryAlgorithmList(direction);
        return AjaxResult.success(info);
    }

    @GetMapping("/passRecord")
    public AjaxResult passRecord(@RequestParam(name = "gantryId",required = false) String gantryId,@RequestParam(name = "startTime",required = false) String startTime, @RequestParam(name = "endTime",required = false) String endTime)
    {
        List<Map> info = gantryAlgorithm.selectGantryAlgorithmPassRecord(gantryId,startTime,endTime);
        return AjaxResult.success(info);
    }

    @GetMapping("/chargeInfo")
    public AjaxResult chargeInfo(@RequestParam(name = "chargingUnitId",required = false) String chargingUnitId) {
        return AjaxResult.success(gantryAlgorithm.selectChargeInfo(chargingUnitId));
    }

    @GetMapping("/selectChargeFlowList")
    public AjaxResult selectChargeFlowList(@RequestParam(name = "chargingUnitId") String chargingUnitId,
                                           @RequestParam(name = "startDate") String startDate,
                                           @RequestParam(name = "endDate") String endDate){
        return AjaxResult.success(gantryAlgorithm.selectChargeFlowList(chargingUnitId, startDate, endDate));
    }

    @GetMapping("/selectChargeFlow")
    public AjaxResult selectChargeFlow(@RequestParam(name = "chargingUnitId") String chargingUnitId,
                                       @RequestParam(name = "statisticsDate") String statisticsDate){
        return AjaxResult.success(gantryAlgorithm.selectChargeFlow(chargingUnitId, statisticsDate));
    }
}
