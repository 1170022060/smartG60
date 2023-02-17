package com.pingok.external.controller.primary;


import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblOLFlowService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/olFlowInfo")
public class OLFlowInfoController {
    @Autowired
    private ITblOLFlowService iTblOLFlowService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblOLFlowService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getOLFlowInfo")
    public AjaxResult getOLFlowInfo(@RequestBody JSONArray result){
        iTblOLFlowService.getOLFlowInfo(result);
        return AjaxResult.success();
    }
}
