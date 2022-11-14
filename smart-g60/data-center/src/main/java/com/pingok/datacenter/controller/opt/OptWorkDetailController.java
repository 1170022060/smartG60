package com.pingok.datacenter.controller.opt;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.opt.IOptWorkDetailService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 排班当岗 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/optWorkDetail")
public class OptWorkDetailController  extends BaseController {

    @Autowired
    private IOptWorkDetailService iOptWorkDetailService;

    @PostMapping
    public AjaxResult optWorkDetail(@RequestBody JSONObject object) {
        log.info("排班当岗----请求参数-----" + object.toJSONString());
        iOptWorkDetailService.optWorkDetail(object);
        return AjaxResult.success();
    }

    @GetMapping
    public AjaxResult makeWorkTable(@RequestParam(name = "workDate") Date workDate) {
        return AjaxResult.success(iOptWorkDetailService.makeWorkTable(workDate));
    }
}
