package com.pingok.datacenter.controller.opt;

import com.alibaba.fastjson.JSONObject;
import com.pingok.datacenter.service.opt.IWorkerShiftService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工班 信息操作处理
 *
 * @author xia
 */
@RestController
@Slf4j
@RequestMapping("/workerShift")
public class WorkerShiftController extends BaseController {

    @Autowired
    private IWorkerShiftService iWorkerShiftService;

    @PostMapping
    public AjaxResult workerShift(@RequestBody JSONObject object) {
//        log.info("工班----请求参数-----" + object.toJSONString());
        iWorkerShiftService.workerShift(object);
        return AjaxResult.success();
    }
}
