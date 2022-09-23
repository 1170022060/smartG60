package com.pingok.vocational.controller.roster;

import com.pingok.vocational.domain.roster.TblSuspectListRecord;
import com.pingok.vocational.service.roster.ISuspectListRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
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

/**
 * 疑似违法车辆名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/suspectList")
public class SuspectListRecordController extends BaseController {

    @Autowired
    private ISuspectListRecordService suspectListRecordService;

    @RequiresPermissions("vocational:suspectList:info")
    @Log(title = "疑似违法车辆名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "vehicleId",required = false) String vehicleId, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<TblSuspectListRecord> info = suspectListRecordService.selectSuspectList(vehicleId ,startTime , endTime);
        return getDataTable(info);
    }
}
