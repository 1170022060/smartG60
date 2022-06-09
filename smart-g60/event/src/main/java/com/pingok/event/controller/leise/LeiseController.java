package com.pingok.event.controller.leise;

import com.alibaba.fastjson.JSONObject;
import com.pingok.event.service.leise.ILeiseService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @time 2022/5/24 10:57
 */
@RestController
@RequestMapping("/leise")
public class LeiseController extends BaseController {
    @Autowired
    private ILeiseService iLeiseService;

    @PostMapping("/object")
    public AjaxResult handleObject(@RequestBody JSONObject objectData) {
        iLeiseService.handleObject(objectData);
        return AjaxResult.success();
    }

    @PostMapping("/event")
    public AjaxResult handleEvent(@RequestBody JSONObject eventData) {
        iLeiseService.handleEvent(eventData);
        return AjaxResult.success();
    }
}
