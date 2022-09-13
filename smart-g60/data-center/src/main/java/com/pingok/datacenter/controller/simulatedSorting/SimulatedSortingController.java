package com.pingok.datacenter.controller.simulatedSorting;

import com.pingok.datacenter.service.simulatedSorting.ISimulatedSortingService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 模拟清分
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/simulatedSorting")
public class SimulatedSortingController extends BaseController {

    @Autowired
    private ISimulatedSortingService iSimulatedSortingService;

    @PostMapping
    public AjaxResult simulatedSorting(@RequestParam(value = "year") String year, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        iSimulatedSortingService.simulatedSorting(year, startTime, endTime);
        return AjaxResult.success();
    }

}
