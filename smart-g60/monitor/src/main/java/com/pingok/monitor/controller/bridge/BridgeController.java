package com.pingok.monitor.controller.bridge;

import com.pingok.monitor.service.bridge.IBridgeStatusService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/bridge")
public class BridgeController extends BaseController {

    @Autowired
    private IBridgeStatusService iBridgeStatusService;

    @RequiresPermissions("monitor:bridge:search")
    @Log(title = "桥梁监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/search")
    public TableDataInfo search(String name) {
        startPage();
        List<Map> list = iBridgeStatusService.list(name);
        return getDataTable(list);
    }

    @RequiresPermissions("monitor:bridge:deviceStatus")
    @Log(title = "桥梁监控服务", businessType = BusinessType.OTHER)
    @GetMapping
    public AjaxResult deviceStatus(@RequestParam Long id) {
        return AjaxResult.success(iBridgeStatusService.deviceStatus(id));
    }
}
