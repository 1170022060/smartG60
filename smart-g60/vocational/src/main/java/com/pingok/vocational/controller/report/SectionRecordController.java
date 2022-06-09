package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.domain.report.vo.SectionRecordVo;
import com.pingok.vocational.service.report.ISectionRecordService;
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
 * 出入口段面记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/section")
public class SectionRecordController extends BaseController {
    @Autowired
    private ISectionRecordService tblSectionRecordService;

    @RequiresPermissions("vocational:section:info")
    @Log(title = "出入口段面记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "stationId",required = false) String stationId, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate, @RequestParam(name = "direction",required = false) Integer direction)
    {
        startPage();
        List<Map> info = tblSectionRecordService.selectSectionRecord( stationId,startDate,endDate,direction);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:section:export")
    @Log(title = "出入口段面记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response,@RequestBody ReportVo reportVo)
    {
        List<SectionRecordVo> list = tblSectionRecordService.selectSectionRecordList(reportVo);
        ExcelUtil<SectionRecordVo> util = new ExcelUtil<SectionRecordVo>(SectionRecordVo.class);
        util.exportExcel(response,list, "出入口段面记录");
    }
}
