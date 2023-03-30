package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.IntransitRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.service.report.IIntransitRecordService;
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
 * 在途总流量记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/intransit")
public class IntransitRecordController extends BaseController {
    @Autowired
    private IIntransitRecordService tblIntransitRecordService;

    @RequiresPermissions("vocational:intransit:info")
    @Log(title = "在途总流量记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = tblIntransitRecordService.selectIntransitRecord(startDate,endDate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:intransit:export")
    @Log(title = "在途总流量记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
//    @ResponseBody
    public void export(HttpServletResponse response, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        List<IntransitRecordVo> list = tblIntransitRecordService.selectIntransitRecordList(startDate,endDate);
        ExcelUtil<IntransitRecordVo> util = new ExcelUtil<IntransitRecordVo>(IntransitRecordVo.class);
        util.exportExcel(response,list, "在途总流量记录");
    }
}
