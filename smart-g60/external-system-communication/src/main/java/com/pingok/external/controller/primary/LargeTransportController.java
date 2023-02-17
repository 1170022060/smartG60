package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.pingok.external.service.primary.ITblLargeTransportService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/largeTransport")
public class LargeTransportController {
    @Autowired
    private ITblLargeTransportService iTblLargeTransportService;

    @GetMapping
    public AjaxResult collect() {
//        开始发起kafka消息
        iTblLargeTransportService.collect();
        return AjaxResult.success();
    }

    @PostMapping("/getLargeTransport")
    public AjaxResult getLargeTransport(@RequestBody JSONArray result){
        iTblLargeTransportService.getLargeTransport(result);
        return AjaxResult.success();
    }
}
