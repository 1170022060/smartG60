package com.pingok.monitor.controller.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 智慧厕所 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/smartToilet")
public class SmartToiletController extends BaseController {
    @Autowired
    private ISmartToiletService iSmartToiletService;

    @PostMapping("/send")
    public AjaxResult send(@RequestBody JSONObject obj) {
        iSmartToiletService.send(obj);
        return AjaxResult.success();
    }

    @PostMapping("/api/v1/apis/toilet/post-sensor-data")
    public AjaxResult sensorData(@RequestBody JSONObject sensorData) {
        iSmartToiletService.sensorData(sensorData);
        return AjaxResult.success();
    }
}
