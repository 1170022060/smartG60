package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IRecoveryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 追缴名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/recovery")
public class RecoveryController extends BaseController {

    @Autowired
    private IRecoveryService iRecoveryService;

    @PostMapping
    public AjaxResult recovery(@RequestBody JSONObject object) {
        log.info("追缴名单----请求参数-----" + object.toJSONString());
        iRecoveryService.recovery(object);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/recoveryIncr")
    public AjaxResult recoveryIncr(@Validated @RequestBody String version)
    {
        iRecoveryService.increment(version);
        return AjaxResult.success();
    }

    @PostMapping("/recoveryAll")
    public AjaxResult recoveryAll(@Validated @RequestBody String version)
    {
        iRecoveryService.all(version);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/unzipRecoveryAll")
    public AjaxResult unzipRecoveryAll(@Validated @RequestBody String version)
    {
        iRecoveryService.unzipAll(version);
        return AjaxResult.success();
    }
}
