package com.pingok.devicemonitor.controller.pilotLight;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @time 2022/5/11 17:28
 */
@Slf4j
@RestController
@RequestMapping("/pilotLight")
public class PilotLightController extends BaseController {

    @Autowired
    private RemoteKafkaService remoteKafkaService;

    /**
     * 引导灯控制
     *
     * @param params：cmdType-预设模式； deviceId-设备ID；
     * @return
     */
    @Log(title = "超视距诱导灯发布", businessType = BusinessType.OTHER)
    @PostMapping("/send")
    public AjaxResult send(@RequestBody JSONObject params) {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        params.put("kafkaType", "commandSend");
        kafkaEnum.setData(JSON.toJSONString(params));
        R r = remoteKafkaService.send(kafkaEnum);
        if (r.getCode() == R.SUCCESS) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(r.getMsg());
        }
    }

    @GetMapping("/updateStatus")
    public AjaxResult updateStatus() {
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT);
        JSONObject params = new JSONObject();
        params.put("kafkaType", "updateStatus");
        kafkaEnum.setData(JSON.toJSONString(params));
        R r = remoteKafkaService.send(kafkaEnum);
        if (r.getCode() == R.SUCCESS) {
            return AjaxResult.success();
        } else {
            return AjaxResult.error(r.getMsg());
        }
    }
}
