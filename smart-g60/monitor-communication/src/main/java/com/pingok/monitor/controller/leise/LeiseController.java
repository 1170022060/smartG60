package com.pingok.monitor.controller.leise;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.service.leise.ILeiseService;
import com.pingok.monitor.service.leise.ILeiseStoreService;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/5/24 10:35
 */
@RestController
@RequestMapping("/leise")
public class LeiseController extends BaseController {
    @Autowired
    private ILeiseService iLeiseService;
    @Autowired
    ILeiseStoreService iLeiseStoreService;

    @Autowired
    private IPilotLightService iPilotLightService;

    @PostMapping("/object")
    public AjaxResult handleObject(@RequestBody JSONObject objectData) {
        iPilotLightService.updateStatus();
        iLeiseStoreService.saveObject(objectData);
        iLeiseService.handleObject(objectData);
        return AjaxResult.success();
    }

    @PostMapping("/event")
    public AjaxResult handleEvent(@RequestBody JSONObject eventData) {
        iLeiseStoreService.saveEvent(eventData);
        iLeiseService.handleEvent(eventData);
        return AjaxResult.success();
    }
}
