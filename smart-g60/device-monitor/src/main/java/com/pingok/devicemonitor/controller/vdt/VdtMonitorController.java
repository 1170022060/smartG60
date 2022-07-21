package com.pingok.devicemonitor.controller.vdt;

import com.pingok.devicemonitor.service.vdt.IVdtService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/7/6 10:43
 */
@Slf4j
@RestController
@RequestMapping("/vdtMonitor")
public class VdtMonitorController extends BaseController {
    @Autowired
    IVdtService iVdtService;

    @GetMapping
    public AjaxResult collect() {
        iVdtService.collect();
        return AjaxResult.success();
    }
}
