package com.pingok.monitor.controller.pilotLight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.service.pilotService.IPilotLightService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/light")
public class PilotLightController extends BaseController {

    @Autowired
    private IPilotLightService iPilotLightService;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @GetMapping("/pilotLightStatus")
    public AjaxResult pilotLightStatus() {
        return AjaxResult.success(iPilotLightService.pilotLightStatus());
    }


    /**
     * 引导灯控制
     *
     * @param params：cmdType-预设模式； deviceId-设备ID；
     * @return
     */
    @PostMapping("/send")
    public AjaxResult send(@RequestBody JSONObject params) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        params.put("kafkaType", "commandSendV2");
        kafkaEnum.setData(JSON.toJSONString(params));
        R r = remoteKafkaService.send(kafkaEnum);
        return AjaxResult.success();
    }

    @GetMapping("/rtStatus")
    public AjaxResult getRtStatus(@RequestParam(required = false) Integer roadId) {
        return AjaxResult.success(iPilotLightService.getRtStatus(roadId));
    }
}
