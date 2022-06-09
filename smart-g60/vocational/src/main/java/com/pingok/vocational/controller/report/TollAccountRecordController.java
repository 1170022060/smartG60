package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.TollAccountRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.service.report.ITollAccountRecordService;
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
 * 通行费核算记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/account")
public class TollAccountRecordController extends BaseController {

    @Autowired
    private ITollAccountRecordService tblTollAccountRecordService;

    @RequiresPermissions("vocational:account:info")
    @Log(title = "通行费核算记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = tblTollAccountRecordService.selectAccountRecord(startDate,endDate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:account:export")
    @Log(title = "通行费核算记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestBody ReportVo reportVo)
    {
        List<TollAccountRecordVo> list = tblTollAccountRecordService.selectAccountRecorList(reportVo);
        ExcelUtil<TollAccountRecordVo> util = new ExcelUtil<TollAccountRecordVo>(TollAccountRecordVo.class);
        util.exportExcel(response,list, "通行费核算记录");
    }
}
