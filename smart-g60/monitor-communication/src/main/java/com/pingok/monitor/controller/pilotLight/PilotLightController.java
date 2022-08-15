package com.pingok.monitor.controller.pilotLight;

import cn.hutool.json.JSONObject;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pilotLight")
public class PilotLightController extends BaseController {
    @Autowired
    private IPilotLightService iPilotLightService;

    @GetMapping
    public AjaxResult testGet() {
        return AjaxResult.success();
    }

    @PostMapping("/user/logout")
    public AjaxResult testPost(@RequestBody JSONObject data) {
        JSONObject dt = new JSONObject();
        dt.set("token", "8e1dc5a4");
        return AjaxResult.success(dt);
    }
}
