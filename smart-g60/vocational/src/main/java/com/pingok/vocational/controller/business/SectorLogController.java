package com.pingok.vocational.controller.business;

import com.pingok.vocational.service.business.ISectorLogService;
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

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 扇区日志
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/sectorLog")
public class SectorLogController extends BaseController {

    @Autowired
    private ISectorLogService sectorLogService;

    @RequiresPermissions("vocational:sectorLog:findByGidAndLaneHex")
    @Log(title = "扇区日志", businessType = BusinessType.OTHER)
    @GetMapping("/findByGidAndLaneHex")
    public AjaxResult findByGidAndLaneHex(@RequestParam String gid, @RequestParam String laneHex) {
        return AjaxResult.success(sectorLogService.findByGidAndLaneHex(gid, laneHex));
    }

    @RequiresPermissions("vocational:sectorLog:info")
    @Log(title = "扇区日志", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime", required = false) Date startTime, @RequestParam(name = "endTime", required = false) Date endTime, @RequestParam(name = "gid", required = false) String gid, @RequestParam(name = "laneId", required = false) String laneId, @RequestParam(name = "passType", required = false) Integer passType) {
        startPage();
        List<Map> info = sectorLogService.selectSectorLog(startTime, endTime, gid, laneId, passType);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:sectorLog:details")
    @Log(title = "扇区日志", businessType = BusinessType.OTHER)
    @GetMapping("/details")
    public AjaxResult details(@RequestParam(name = "logId") Long logId) {
        return AjaxResult.success(sectorLogService.selectDetails(logId));
    }
}
