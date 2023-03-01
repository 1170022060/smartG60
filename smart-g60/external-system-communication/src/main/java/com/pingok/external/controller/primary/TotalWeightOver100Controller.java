package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblTotalWeightOver100Service;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/totalWeightOver100")
public class TotalWeightOver100Controller {
    @Autowired
    private ITblTotalWeightOver100Service tblTotalWeightOver100Service;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        tblTotalWeightOver100Service.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getTotalWeightOver100")
    public AjaxResult getTotalWeightOver100(@RequestBody JSONArray result){
        tblTotalWeightOver100Service.getTotalWeightOver100(result);
        return AjaxResult.success();
    }
}
