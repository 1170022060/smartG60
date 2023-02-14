package com.pingok.external.controller.primary;


import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.IPrimaryService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/primary")
public class primaryController {
    @Autowired
    private IPrimaryService iPrimaryService;

    @PostMapping("/getPrimaryGps")
    public AjaxResult getPrimaryGps(@RequestBody JSONArray result){
        iPrimaryService.getPrimaryGps(result);
        return AjaxResult.success();
    }

    @PostMapping("/getTruckOverWeight")
    public AjaxResult getTruckOverWeight(@RequestBody JSONArray result){
        iPrimaryService.getTruckOverWeight(result);
        return AjaxResult.success();
    }

    @PostMapping("/getOWInfo")
    public AjaxResult getOWInfo(@RequestBody JSONArray result){
        iPrimaryService.getOWInfo(result);
        return AjaxResult.success();
    }

    @PostMapping("/getOLFlowInfo")
    public AjaxResult getOLFlowInfo(@RequestBody JSONArray result){
        iPrimaryService.getOLFlowInfo(result);
        return AjaxResult.success();
    }

    @PostMapping("/getStationLLInfo")
    public AjaxResult getStationLLInfo(@RequestBody JSONArray result){
        iPrimaryService.getStationLLInfo(result);
        return AjaxResult.success();
    }

    @PostMapping("/getOLFlowAndRate")
    public AjaxResult getOLFlowAndRate(@RequestBody JSONArray result){
        iPrimaryService.getOLFlowAndRate(result);
        return AjaxResult.success();
    }

    @PostMapping("/getOLWeightAndRate")
    public AjaxResult getOLWeightAndRate(@RequestBody JSONArray result){
        iPrimaryService.getOLWeightAndRate(result);
        return AjaxResult.success();
    }

    @PostMapping("/getTotalWeightOver100")
    public AjaxResult getTotalWeightOver100(@RequestBody JSONArray result){
        iPrimaryService.getTotalWeightOver100(result);
        return AjaxResult.success();
    }

    @PostMapping("/getLargeTransportInfo")
    public AjaxResult getLargeTransportInfo(@RequestBody JSONArray result){
        iPrimaryService.getLargeTransportInfo(result);
        return AjaxResult.success();
    }

}
