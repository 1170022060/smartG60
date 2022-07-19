package com.pingok.event.controller.radarCamera;

import com.alibaba.fastjson.JSONObject;
import com.pingok.event.service.radarCamera.IRadarCameraService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import oracle.ucp.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/7/15 10:21
 */
@RestController
@RequestMapping("/radarCamera")
public class RadarCameraController extends BaseController {

    @Autowired
    private IRadarCameraService iRadarCameraService;

    @PostMapping
    public AjaxResult handleTraffic(@RequestBody JSONObject data) {
        iRadarCameraService.handleTraffic(data);
        return AjaxResult.success();
    }
}
