package com.pingok.vocational.controller.roster;

import com.pingok.vocational.service.roster.IPursuesListRecordService;
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
 * 追讨名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/pursuesList")
public class PursuesListRecordController extends BaseController {
    @Autowired
    private IPursuesListRecordService pursuesListRecordService;

    @RequiresPermissions("vocational:pursuesList:info")
    @Log(title = "追讨名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> info = pursuesListRecordService.selectPursuesList(startTime,endTime);
        return getDataTable(info);
    }
}
