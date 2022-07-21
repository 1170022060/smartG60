package com.pingok.vocational.controller.roster;

import com.pingok.vocational.domain.roster.TblEpidemicListRecord;
import com.pingok.vocational.service.roster.IEpidemicListRecordService;
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
 * 中高风险地区名单
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/epidemicList")
public class EpidemicListRecordController extends BaseController {

    @Autowired
    private IEpidemicListRecordService epidemicListRecordService;

    @RequiresPermissions("vocational:epidemicList:info")
    @Log(title = "中高风险地区名单-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<TblEpidemicListRecord> info = epidemicListRecordService.selectEpidemicList(startTime , endTime);
        return getDataTable(info);
    }
}
