package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SpecialRecordVo;
import com.pingok.vocational.service.report.ISpecialRecordService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 特情记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/special")
public class SpecialRecordController extends BaseController {

    @Autowired
    private ISpecialRecordService specialRecordService;

    @RequiresPermissions("vocational:special:info")
    @Log(title = "特情记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime,@RequestParam(name = "stationId",required = false) String stationId,@RequestParam(name = "laneId",required = false) String laneId)
    {
        startPage();
        List<Map> info = specialRecordService.selectSpecialRecord(startTime,endTime,stationId,laneId);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:special:statistic")
    @Log(title = "特情统计-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/statistic")
    public TableDataInfo statistic(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = specialRecordService.selectSpecialStatistic(startDate,endDate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:special:export")
    @Log(title = "特情统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<SpecialRecordVo> list = specialRecordService.selectSpecialStatisticList(reportVo);
        ExcelUtil<SpecialRecordVo> util = new ExcelUtil<SpecialRecordVo>(SpecialRecordVo.class);
        util.exportExcel(response,list, "特情统计");
    }
}
