package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblOlFlowAndRateService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/olFlowAndRate")
public class OlFlowAndRateController {
    @Autowired
    private ITblOlFlowAndRateService iTblOlFlowAndRateService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblOlFlowAndRateService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getOlFlowAndRate")
    public AjaxResult getOlFlowAndRate(@RequestBody JSONArray result){
        iTblOlFlowAndRateService.getOlFlowAndRateInfo(result);
        return AjaxResult.success();
    }
}
