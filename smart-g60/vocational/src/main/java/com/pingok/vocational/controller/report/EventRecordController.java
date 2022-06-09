package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.*;
import com.pingok.vocational.service.report.IEventRecordService;
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
 * 事件记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/eventRecord")
public class EventRecordController extends BaseController {

    @Autowired
    private IEventRecordService eventRecordService;

    @RequiresPermissions("vocational:eventRecord:type")
    @Log(title = "事件统计(按事件类型)-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/type")
    public TableDataInfo type(@RequestParam(name = "eventType",required = false) String eventType, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> type = eventRecordService.selectEventRecordByType
                (eventType, startTime , endTime);
        return getDataTable(type);
    }

    @RequiresPermissions("vocational:eventRecord:exportType")
    @Log(title = "事件统计(按事件类型)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportType")
    @ResponseBody
    public void exportType(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<EventRecordTypeVo> list = eventRecordService.selectEventRecordByTypeList(reportVo);
        ExcelUtil<EventRecordTypeVo> util = new ExcelUtil<EventRecordTypeVo>(EventRecordTypeVo.class);
        util.exportExcel(response,list, "事件统计(按事件类型)");
    }

    @RequiresPermissions("vocational:eventRecord:site")
    @Log(title = "事件统计(按位置区间)-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/site")
    public TableDataInfo site(@RequestParam(name = "locationInterval",required = false) String locationInterval, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> type = eventRecordService.selectEventRecordBySite
                (locationInterval, startTime , endTime);
        return getDataTable(type);
    }

    @RequiresPermissions("vocational:eventRecord:exportSite")
    @Log(title = "事件统计(按位置区间)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSite")
    @ResponseBody
    public void exportSite(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<EventRecordSiteVo> list = eventRecordService.selectEventRecordBySiteList(reportVo);
        ExcelUtil<EventRecordSiteVo> util = new ExcelUtil<EventRecordSiteVo>(EventRecordSiteVo.class);
        util.exportExcel(response,list, "事件统计(按位置区间)");
    }

    @RequiresPermissions("vocational:eventRecord:vClass")
    @Log(title = "事件统计(按车型)-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/vClass")
    public TableDataInfo vClass(@RequestParam(name = "vehClass",required = false) Integer vehClass, @RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> vClass = eventRecordService.selectEventRecordByClass
                (vehClass, startTime , endTime);
        return getDataTable(vClass);
    }

    @RequiresPermissions("vocational:eventRecord:exportClass")
    @Log(title = "事件统计(按车型)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportClass")
    @ResponseBody
    public void exportClass(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<EventRecordClassVo> list = eventRecordService.selectEventRecordByClassList(reportVo);
        ExcelUtil<EventRecordClassVo> util = new ExcelUtil<EventRecordClassVo>(EventRecordClassVo.class);
        util.exportExcel(response,list, "事件统计(按车型)");
    }
}
