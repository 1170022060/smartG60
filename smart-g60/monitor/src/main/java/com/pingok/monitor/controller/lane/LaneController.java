package com.pingok.monitor.controller.lane;

import com.pingok.monitor.service.lane.ILaneService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 车道 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/lane")
public class LaneController extends BaseController {

    @Autowired
    private ILaneService iLaneService;

    @RequiresPermissions("monitor:lane:findByStationId")
    @Log(title = "车道监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult findByStationId(@RequestParam String stationId) {
        return AjaxResult.success(iLaneService.findByStationId(stationId));
    }
}
