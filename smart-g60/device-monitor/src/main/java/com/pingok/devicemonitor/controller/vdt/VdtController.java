package com.pingok.devicemonitor.controller.vdt;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.vdt.IVdtService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/vdt")
public class VdtController {

    @Autowired
    private IVdtService iVdtService;

    @PostMapping("/notifyResult")
    public AjaxResult notifyResult(@RequestBody JSONArray result) {
        iVdtService.notifyResult(result);
        return AjaxResult.success();
    }
}
