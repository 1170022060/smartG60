package com.pingok.vocational.controller.road;

import com.pingok.vocational.service.road.IRoadService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
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
@RequestMapping("/road")
public class RoadController extends BaseController {
    @Autowired
    private IRoadService iRoadService;

    @GetMapping("/roadVideoEvent")
    public TableDataInfo getRoadVideoEvent(@RequestParam(name = "type",required = false)Integer type){
        startPage();
        List<Map> info = iRoadService.selectRoadVideoEvent(type);
        return getDataTable(info);
    }

    @GetMapping("/roadStatisEvent")
    public AjaxResult getRoadStatisEvent(@RequestParam(name = "vehPlate",required = false)String vehPlate,
                                         @RequestParam(name = "startTime",required = false)Date startTime,
                                         @RequestParam(name = "endTime",required = false)Date endTime){
        return AjaxResult.success(iRoadService.selectRoadStatisEvent(vehPlate,startTime,endTime));
    }
}
