package com.pingok.datacenter.controller.lane;

import com.pingok.datacenter.domain.lane.TblLaneStatus;
import com.pingok.datacenter.service.lane.ILaneStatusService;
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
 * 车道状态 信息操作处理
 *
 * @author qiumin
 */
@RestController
@Slf4j
@RequestMapping("/laneStatus")
public class LaneStatusController extends BaseController {
    @Autowired
    private ILaneStatusService iLaneStatusService;


    @PostMapping
    public AjaxResult update(@Validated @RequestBody TblLaneStatus tblLaneStatus) {
        log.info("车道状态update----请求参数-----" + tblLaneStatus);
        iLaneStatusService.update(tblLaneStatus);
        return AjaxResult.success();
    }
}
