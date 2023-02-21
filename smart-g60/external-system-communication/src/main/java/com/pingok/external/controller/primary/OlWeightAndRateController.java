package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblOlFlowAndRateService;
import com.pingok.external.service.primary.ITblOlWeightAndRateService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/olWeightAndRate")
public class OlWeightAndRateController {
    @Autowired
    private ITblOlWeightAndRateService iTblOlWeightAndRateService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblOlWeightAndRateService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getOlWeightAndRate")
    public AjaxResult getOlWeightAndRate(@RequestBody JSONArray result){
        iTblOlWeightAndRateService.getOlWeightAndRateInfo(result);
        return AjaxResult.success();
    }
}
