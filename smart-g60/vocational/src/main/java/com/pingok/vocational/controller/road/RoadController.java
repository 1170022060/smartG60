package com.pingok.vocational.controller.road;

import com.pingok.vocational.service.road.IRoadService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/road")
public class RoadController extends BaseController {
    @Autowired
    private IRoadService iRoadService;

    @GetMapping("/roadVideoEvent")
    public AjaxResult getRoadVideoEvent(){
        return AjaxResult.success(iRoadService.selectRoadVideoEvent());
    }

    @GetMapping("/roadStatisEvent")
    public AjaxResult getRoadStatisEvent(){
        return AjaxResult.success(iRoadService.selectRoadStatisEvent());
    }
}
