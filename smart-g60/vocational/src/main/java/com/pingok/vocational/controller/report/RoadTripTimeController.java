package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.RoadTripTimeVo;
import com.pingok.vocational.service.report.IRoadTripTimeService;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
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
 * 路段行程时间
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/roadTrip")
public class RoadTripTimeController extends BaseController {

    @Autowired
    private IRoadTripTimeService roadTripTimeService;

    @RequiresPermissions("vocational:roadTrip:info")
    @Log(title = "路段行程时间-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "hour",required = false) Integer hour, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = roadTripTimeService.selectRoadTripTime(hour,startDate,endDate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:roadTrip:export")
    @Log(title = "路段行程时间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<RoadTripTimeVo> list = roadTripTimeService.selectRoadTripTimeList(reportVo);
        ExcelUtil<RoadTripTimeVo> util = new ExcelUtil<RoadTripTimeVo>(RoadTripTimeVo.class);
        util.exportExcel(response,list, "路段行程时间");
    }


    @RequiresPermissions("vocational:roadTrip:add")
    @Log(title = "路段行程时间统计入库", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public AjaxResult add()
    {
        return toAjax(roadTripTimeService.insertTripTimeHour());
    }
}
