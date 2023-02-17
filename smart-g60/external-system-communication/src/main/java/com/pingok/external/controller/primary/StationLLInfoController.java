package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblOLFlowService;
import com.pingok.external.service.primary.ITblStationLLService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/stationLLInfo")
public class StationLLInfoController {
    @Autowired
    private ITblStationLLService iTblStationLLService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblStationLLService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getStationLLInfo")
    public AjaxResult getStationLLInfo(@RequestBody JSONArray result){
        iTblStationLLService.getStationLLInfo(result);
        return AjaxResult.success();
    }
}
