package com.pingok.vocational.controller.assist;

import com.pingok.vocational.service.assist.IRoadForecastService;
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
@RequestMapping("/roadForecast")
public class RoadForecastController extends BaseController {

    @Autowired
    private IRoadForecastService iRoadForecastService;

    @GetMapping
    public TableDataInfo getInfo(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate){
        startPage();
        List<Map> info = iRoadForecastService.roadForecast(startDate,endDate);
        return getDataTable(info);
    }
}
