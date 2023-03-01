package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IEpidemicService;
import com.pingok.datacenter.service.roster.IGreenService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 绿通名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/green")
public class GreenController extends BaseController {

    @Autowired
    private IGreenService iGreenService;

    @PostMapping
    public AjaxResult green(@RequestBody JSONObject object) {
        log.info("绿通名单----请求参数-----" + object.toJSONString());
        iGreenService.green(object);
        return AjaxResult.success();
    }

    @PostMapping("/greenDownload")
    public AjaxResult greenDownload()
    {
        iGreenService.greenDownload();
        return AjaxResult.success();
    }

}
