package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IEpidemicService;
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
 * 中高风险名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/epidemic")
public class EpidemicController extends BaseController {

    @Autowired
    private IEpidemicService iEpidemicService;

    @PostMapping
    public AjaxResult epidemic(@RequestBody JSONObject object) {
        log.info("中高风险名单----请求参数-----" + object.toJSONString());
        iEpidemicService.epidemic(object);
        return AjaxResult.success();
    }

    @PostMapping("/prefix")
    public AjaxResult epidemicPrefix(@RequestBody JSONObject object) {
        log.info("中高风险车牌名单----请求参数-----" + object.toJSONString());
        iEpidemicService.epidemicPrefix(object);
        return AjaxResult.success();
    }

    @PostMapping("/epidemicDownload")
    public AjaxResult epidemicDownload()
    {
        iEpidemicService.epidemicDownload();
        return AjaxResult.success();
    }

    @PostMapping("/prefixDownload")
    public AjaxResult prefixDownload()
    {
        iEpidemicService.prefixDownload();
        return AjaxResult.success();
    }

}
