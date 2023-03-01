package com.pingok.datacenter.controller.roster;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.roster.IOverLoadService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 超限车辆名单 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/overload")
public class OverLoadController extends BaseController {

    @Autowired
    private IOverLoadService iOverLoadService;

    @PostMapping
    public AjaxResult overLoad(@RequestBody JSONObject object) {
        log.info("超限车辆名单----请求参数-----" + object.toJSONString());
        iOverLoadService.overLoad(object);
        return AjaxResult.success();
    }
}
