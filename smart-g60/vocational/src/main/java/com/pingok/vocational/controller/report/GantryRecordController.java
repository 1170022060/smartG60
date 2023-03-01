package com.pingok.vocational.controller.report;

import com.pingok.vocational.domain.report.vo.GantryRecordVo;
import com.pingok.vocational.domain.report.vo.ReportVo;
import com.pingok.vocational.service.report.IGantryRecordService;
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
 * 门架段面记录
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/gantry")
public class GantryRecordController extends BaseController {

    @Autowired
    private IGantryRecordService tblGantryRecordService;

//    @RequiresPermissions("vocational:gantry:info")
//    @Log(title = "门架段面记录-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "gantryId",required = false) String gantryId, @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = tblGantryRecordService.selectGantryRecord
                (gantryId, startDate , endDate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:gantry:export")
    @Log(title = "门架段面记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response,@RequestBody ReportVo reportVo)
    {
        List<GantryRecordVo> list = tblGantryRecordService.selectGantryRecordList(reportVo);
        ExcelUtil<GantryRecordVo> util = new ExcelUtil<GantryRecordVo>(GantryRecordVo.class);
        util.exportExcel(response,list, "门架段面记录");
    }

    @GetMapping("/getGantryFlow")
    public TableDataInfo getGantryFlow(@RequestParam(name = "gantryId",required = false) String gantryId,
                                       @RequestParam(name = "startDate",required = false) Date startDate,
                                       @RequestParam(name = "endDate",required = false) Date endDate){
        startPage();
        List<Map> info = tblGantryRecordService.selectGantryFlow(gantryId,startDate,endDate);
        return getDataTable(info);
    }
}
