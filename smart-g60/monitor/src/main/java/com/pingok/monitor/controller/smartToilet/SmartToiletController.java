package com.pingok.monitor.controller.smartToilet;

import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequiresPermissions("monitor:smartToilet:findByFieldNum")
    @Log(title = "智慧厕所监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByFieldNum(@RequestParam String fieldNum) {
        return AjaxResult.success(iSmartToiletService.findByFieldNum(fieldNum));
    }
}
