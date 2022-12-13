package com.pingok.datacenter.controller.rush;

import com.pingok.datacenter.domain.rush.TblRushRecord;
import com.pingok.datacenter.service.rush.IRushService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 闯关管理
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/rush")
@Slf4j
public class RushController extends BaseController {

    @Autowired
    private IRushService iRushService;

    @RequiresPermissions("data-center:rush:rushConfirm")
    @Log(title = "闯关管理", businessType = BusinessType.UPDATE)
    @PutMapping("/rushConfirm")
    public AjaxResult rushConfirm(@RequestParam(value = "id") Long id, @RequestParam(value = "status") Integer status) {
        iRushService.rushConfirm(id, status);
        return AjaxResult.success();
    }

    @GetMapping("/list")
    public TableDataInfo list(String stationName, String vehPlate, String startTime, String endTime) {
        startPage();
        List<TblRushRecord> list = iRushService.list(stationName, vehPlate, startTime, endTime);
        return getDataTable(list);
    }

    @PostMapping
    public AjaxResult rushRecord(@RequestParam(value = "year") String year, @RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime, @RequestParam(value = "twoHours") String twoHours) {
        log.info("rushRecord----------year:" + year + "---startTime:" + startTime + "---endTime:" + endTime + "---twoHours:" + twoHours);
        iRushService.rushRecord(year, startTime, endTime, twoHours);
        return AjaxResult.success();
    }

    @GetMapping("/detail")
    public AjaxResult detail(String passId) {
        Map detail = iRushService.detail(passId);
        return AjaxResult.success(detail);
    }
}
