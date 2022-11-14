package com.pingok.monitor.controller.gantry;

import com.pingok.monitor.domain.gantry.vo.GantryV2X;
import com.pingok.monitor.service.gantry.IGantryService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 门架 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/gantry")
public class GantryController extends BaseController {
    @Autowired
    private IGantryService iGantryService;

//    @RequiresPermissions("monitor:gantry:findById")
//    @Log(title = "门架监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/findById")
    public AjaxResult findById(@RequestParam Long id) {
        return AjaxResult.success(iGantryService.findById(id));
    }

//    @RequiresPermissions("monitor:gantry:gantryStatus")
//    @Log(title = "门架监控服务", businessType = BusinessType.OTHER)
    @GetMapping("/gantryStatus")
    public AjaxResult gantryStatus() {
        return AjaxResult.success(iGantryService.gantryStatus());
    }

    @Log(title = "门架车路协同", businessType = BusinessType.OTHER)
    @PostMapping("/gantryV2X")
    public AjaxResult gantryV2X(@RequestBody GantryV2X data) {
        return iGantryService.gantryV2X(data) ? AjaxResult.success() : AjaxResult.error();
    }

    @GetMapping("/getRecord")
    public TableDataInfo getList(String gantryId, String eventType, Integer status, Date startTime, Date endTime){
        startPage();
        List<Map> info = iGantryService.getRecord(gantryId,eventType,status,startTime,endTime);
        return getDataTable(info);
    }
}
