package com.pingok.vocational.controller.assist;

import com.pingok.vocational.service.assist.ITripTimeRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
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
 * 行程时间预测记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/tripTime")
public class TripTimeRecordController extends BaseController {
    @Autowired
    private ITripTimeRecordService tblTripTimeRecordService;

    @RequiresPermissions("vocational:tripTime:info")
    @Log(title = "行程时间预测查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = tblTripTimeRecordService.selectTripTime(startDate,endDate);
        return getDataTable(info);
    }
}
