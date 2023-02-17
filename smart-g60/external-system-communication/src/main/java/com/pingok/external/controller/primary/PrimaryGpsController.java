package com.pingok.external.controller.primary;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.service.primary.ITblPrimaryGpsService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/primaryGps")
public class PrimaryGpsController {

    @Autowired
    private ITblPrimaryGpsService iTblPrimaryGpsService;

    @PostMapping("/getPrimaryGps")
    public AjaxResult getPrimaryGps(@RequestBody JSONObject result){
        iTblPrimaryGpsService.getPrimaryGps(result);
        return AjaxResult.success();
    }
}
