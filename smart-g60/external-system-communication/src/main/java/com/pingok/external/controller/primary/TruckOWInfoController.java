package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblTruckOWService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@Slf4j
@RequestMapping("/truckOW")
public class TruckOWInfoController {
    @Autowired
    private ITblTruckOWService iTblTruckOWService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblTruckOWService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getTruckOWInfo")
    public AjaxResult getTruckOWInfo(@RequestBody JSONArray result) {
        iTblTruckOWService.getTruckOWInfo(result);
        return AjaxResult.success();
    }
}
