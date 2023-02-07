package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.OdRecordStaVo;
import com.pingok.vocational.domain.report.vo.OdRecordVelVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.service.report.IOdRecordService;
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
 * 车辆OD统计记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/od")
public class OdRecordController extends BaseController {

    @Autowired
    private IOdRecordService tblOdRecordService;

    @RequiresPermissions("vocational:od:infoSta")
    @Log(title = "车辆OD统计记录(分站)-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/infoSta")
    public TableDataInfo infoSta(@RequestParam(name = "stationId",required = false) String stationId, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate, @RequestParam(name = "hour",required = false) Integer hour)
    {
        startPage();
        List<Map> infoSta = tblOdRecordService.selectOdRecordBySta(stationId,startDate,endDate,hour);
        return getDataTable(infoSta);
    }

    @RequiresPermissions("vocational:od:exportSta")
    @Log(title = "车辆OD统计记录(分站)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportSta")
    @ResponseBody
    public void exportSta(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<OdRecordStaVo> list = tblOdRecordService.selectOdRecordByStaList(reportVo);
        ExcelUtil<OdRecordStaVo> util = new ExcelUtil<OdRecordStaVo>(OdRecordStaVo.class);
        util.exportExcel(response,list, "车辆OD统计记录(分站)");
    }

//    @RequiresPermissions("vocational:od:infoClass")
    @Log(title = "车辆OD统计记录(分车型)-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/infoClass")
    public TableDataInfo infoClass(@RequestParam(name = "vehClass",required = false) Integer vehClass, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate, @RequestParam(name = "hour",required = false) Integer hour)
    {
        startPage();
        List<Map> info = tblOdRecordService.selectOdRecordByClass(vehClass,startDate,endDate,hour);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:od:exportClass")
    @Log(title = "车辆OD统计记录(分车型)", businessType = BusinessType.EXPORT)
    @PostMapping("/exportClass")
    @ResponseBody
    public void exportClass(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<OdRecordVelVo> list = tblOdRecordService.selectOdRecordByClassList(reportVo);
        ExcelUtil<OdRecordVelVo> util = new ExcelUtil<OdRecordVelVo>(OdRecordVelVo.class);
        util.exportExcel(response,list, "车辆OD统计记录(分车型)");
    }
}
