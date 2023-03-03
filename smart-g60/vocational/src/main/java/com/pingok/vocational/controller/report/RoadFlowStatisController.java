package com.pingok.vocational.controller.report;

import com.pingok.vocational.service.report.IRoadFlowStatisService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/roadFlow")
public class RoadFlowStatisController extends BaseController {

    @Autowired
    private IRoadFlowStatisService iRoadFlowStatisService;

    @GetMapping("/roadFlowStatis")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate){
        startPage();
        List<Map> info = iRoadFlowStatisService.roadFlowStatis(startDate,endDate);
        return getDataTable(info);
    }
}
