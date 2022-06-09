package com.pingok.kafka.controller;


import com.pingok.kafka.domain.TblKafkaFailInfo;
import com.pingok.kafka.service.KafkaService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController extends BaseController {

    @Autowired
    private KafkaService kafkaService;

    @PostMapping
    public AjaxResult send(@Validated @RequestBody TblKafkaFailInfo tblKafkaFailInfo) {
        kafkaService.send(tblKafkaFailInfo);
        return AjaxResult.success();
    }


    @GetMapping
    public AjaxResult findAll() {
        return AjaxResult.success(kafkaService.findAll());
    }

    @DeleteMapping
    public AjaxResult delete(@RequestParam("id") Long id) {
        return toAjax(kafkaService.delete(id));
    }

}
