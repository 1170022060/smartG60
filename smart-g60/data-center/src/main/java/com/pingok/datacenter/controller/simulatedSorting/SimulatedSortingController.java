package com.pingok.datacenter.controller.simulatedSorting;

import com.pingok.datacenter.domain.simulatedSorting.vo.SimulatedSortingVo;
import com.pingok.datacenter.service.simulatedSorting.ISimulatedSortingService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 模拟清分
 *
 * @author ruoyi
 */
@Slf4j
@RestController
@RequestMapping("/simulatedSorting")
public class SimulatedSortingController extends BaseController {

    @Autowired
    private ISimulatedSortingService iSimulatedSortingService;

    @GetMapping("/dayStatistics")
    public TableDataInfo dayStatistics(String startTime, String endTime) {
        startPage();
        List<SimulatedSortingVo> list = iSimulatedSortingService.dayStatistics(startTime, endTime);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult simulatedSorting(@RequestParam(value = "year") String year, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        log.info("rushRecord----------year:" + year + "---startTime:" + startTime + "---endTime:" + endTime);
        iSimulatedSortingService.simulatedSorting(year, startTime, endTime);
        return AjaxResult.success();
    }

}
