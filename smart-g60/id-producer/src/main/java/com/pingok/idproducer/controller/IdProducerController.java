package com.pingok.idproducer.controller;


import com.pingok.idproducer.service.IdProducerService;
import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/idProducer")
public class IdProducerController extends BaseController {

    @Autowired
    private IdProducerService idProducerService;

    @GetMapping
    public Long nextId() {
        return idProducerService.nextId();
    }

}
