package com.pingok.vocational.controller.roster;

import com.pingok.vocational.service.roster.IRescueListRecordService;
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
import java.util.Map;

/**
 * 抢险救灾名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/rescueList")
public class RescueListRecordController extends BaseController {

    @Autowired
    private IRescueListRecordService tblRescueListRecordService;

    @RequiresPermissions("vocational:rescueList:info")
    @Log(title = "抢险救灾名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "vehPlate",required = false) String vehPlate, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> info = tblRescueListRecordService.selectRescueList(vehPlate, startTime , endTime);
        return getDataTable(info);
    }
}
