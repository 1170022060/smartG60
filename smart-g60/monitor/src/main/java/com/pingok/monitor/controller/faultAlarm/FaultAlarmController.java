package com.pingok.monitor.controller.faultAlarm;

import com.pingok.monitor.service.faultAlarm.IFaultAlarmService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 服务区监控 异常告警
 * @author lal
 */
@RestController
@RequestMapping("/serviceMonitor")
public class FaultAlarmController extends BaseController {
    @Autowired
    private IFaultAlarmService iFaultAlarmService;

    @GetMapping
    public TableDataInfo list(){
        startPage();
        List<Map> info = iFaultAlarmService.getFaultAlarm();
        return getDataTable(info);
    }

//    @RequiresPermissions("monitor:serviceMonitor:alarmConfirm")
    @Log(title = "服务区监控-告警确认",businessType = BusinessType.UPDATE)
    @PutMapping("/alarmConfirm")
    public AjaxResult confirm(@RequestParam(name = "type") Integer type,@RequestParam(name = "id")Long id,@RequestParam(name = "alarmStatus")Integer alarmStatus){
        return toAjax(iFaultAlarmService.alarmConfirm(type,id,alarmStatus));
    }
}
