package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IRateService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 最小费率名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/rate")
public class RateController extends BaseController {

    @Autowired
    private IRateService iRateService;

    @PostMapping
    public AjaxResult overLoad(@RequestBody JSONObject object) {
        log.info("最小费率名单----请求参数-----" + object.toJSONString());
        iRateService.rate(object);
        return AjaxResult.success();
    }

    @PostMapping("/rateDownload")
    public AjaxResult rateDownload(@Validated @RequestBody String version)
    {
        iRateService.rateDownload(version);
        return AjaxResult.success();
    }
}