package com.pingok.algorithmBeiJing.controller;

import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.service.IRocketMqService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 算法服务 信息操作处理
 *
 * @author qiumin
 */
@Slf4j
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController extends BaseController {

    @Autowired
    private IRocketMqService iRocketMqService;

    @PostMapping("/gantryTransactionLog")
    public AjaxResult gantryTransactionLog(@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime) {
        log.info("推送交易数据请求参数：startTime:" + startTime + "，endTime:" + endTime);
        iRocketMqService.gantryTransactionLog(startTime, endTime);
        return AjaxResult.success();
    }

    @PostMapping("/sendRoads")
    @Log(title = "算法服务-发送路网结构", businessType = BusinessType.OTHER)
    public AjaxResult sendRoads() {
        iRocketMqService.sendRoads();
        return AjaxResult.success();
    }

}
