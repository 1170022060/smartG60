package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IRescueService;
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
 * 抢险救灾名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/rescue")
public class RescueController extends BaseController {

    @Autowired
    private IRescueService iRescueService;

    @PostMapping
    public AjaxResult rescue(@RequestBody JSONObject object) {
        log.info("抢险救灾名单----请求参数-----" + object.toJSONString());
        iRescueService.rescue(object);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/rescueIncr")
    public AjaxResult rescueIncr()
    {
        iRescueService.increment();
        return AjaxResult.success();
    }

    @PostMapping("/rescueAll")
    public AjaxResult rescueAll(@Validated @RequestBody String version)
    {
        iRescueService.all(version);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/unzipRescueAll")
    public AjaxResult unzipRescueAll(@Validated @RequestBody String version)
    {
        iRescueService.unzipAll(version);
        return AjaxResult.success();
    }
}
