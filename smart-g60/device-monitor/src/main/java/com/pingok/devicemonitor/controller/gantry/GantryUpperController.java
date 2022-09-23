package com.pingok.devicemonitor.controller.gantry;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.service.gantry.IGantryUpperService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/5/23 12:35
 */
@RestController
@RequestMapping("/gantryUpper")
public class GantryUpperController {
    @Autowired
    private IGantryUpperService iGantryUpperService;

    @PostMapping("/baseInfoUpload")
    public AjaxResult handleBaseInfoUpload(@RequestBody JSONObject data) {
        iGantryUpperService.handleBaseInfoUpload(data);
        return AjaxResult.success();
    }

    @PostMapping("/tghbu")
    public AjaxResult handleTghbu(@RequestBody JSONObject data) {
        iGantryUpperService.handleTghbu(data);
        return AjaxResult.success();
    }

    @PostMapping("/specialEventUpload")
    public AjaxResult handleSpecialEventUpload(@RequestBody JSONObject data) {
        iGantryUpperService.handleSpecialEventUpload(data);
        return AjaxResult.success();
    }

}
