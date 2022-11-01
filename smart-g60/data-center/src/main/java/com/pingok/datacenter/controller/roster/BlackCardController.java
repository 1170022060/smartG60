package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IBlackCardService;
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
 * 状态名单 信息操作处理
 *
 * @author qiumin
 */
@RestController
@Slf4j
@RequestMapping("/blackcard")
public class BlackCardController extends BaseController {
    @Autowired
    private IBlackCardService iBlackCardService;


    @PostMapping
    public AjaxResult blackCard(@RequestBody JSONObject object) {
        log.info("状态名单----请求参数-----" + object.toJSONString());
        iBlackCardService.blackCard(object);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/blackIncr")
    public AjaxResult blackIncr()
    {
        iBlackCardService.increment();
        return AjaxResult.success();
    }

    @PostMapping("/blackAll")
    public AjaxResult blackAll(@Validated @RequestBody String version)
    {
        iBlackCardService.all(version);
        return AjaxResult.success();
    }

    @Transactional
    @PostMapping("/unzipBlackAll")
    public AjaxResult unzipBlackAll(@Validated @RequestBody String version)
    {
        iBlackCardService.unzipAll(version);
        return AjaxResult.success();
    }
}
