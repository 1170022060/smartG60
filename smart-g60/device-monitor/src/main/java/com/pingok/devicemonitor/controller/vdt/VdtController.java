package com.pingok.devicemonitor.controller.vdt;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.vdt.IVdtService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class VdtController {

    @Autowired
    private IVdtService iVdtService;

    @PostMapping("/notifyResult")
    public AjaxResult notifyResult(@RequestBody JSONObject result) {
        iVdtService.notifyResult(result);
        return AjaxResult.success();
    }
}
