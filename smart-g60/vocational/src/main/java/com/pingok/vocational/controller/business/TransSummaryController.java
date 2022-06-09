package com.pingok.vocational.controller.business;

import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.domain.business.vo.TransSummaryVo;
import com.pingok.vocational.service.business.ITransSummaryService;
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
 * 流水汇总
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/transSummary")
public class TransSummaryController extends BaseController {

    @Autowired
    private ITransSummaryService transSummaryService;

    @RequiresPermissions("vocational:transSummary:info")
    @Log(title = "流水汇总-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "enStartTime",required = false) Date enStartTime,
                              @RequestParam(name = "enEndTime",required = false) Date enEndTime,
                              @RequestParam(name = "enWorkDate",required = false) Date enWorkDate,
                              @RequestParam(name = "enStationId",required = false) String enStationId,
                              @RequestParam(name = "passId",required = false) String passId,
                              @RequestParam(name = "enGid",required = false) String enGid,
                              @RequestParam(name = "enPassType",required = false) Integer enPassType,
                              @RequestParam(name = "enShift",required = false) Integer enShift,
                              @RequestParam(name = "enVehPlate",required = false) String enVehPlate,
                              @RequestParam(name = "enCardId",required = false) String enCardId,
                              @RequestParam(name = "exStartTime",required = false) Date exStartTime,
                              @RequestParam(name = "exEndTime",required = false) Date exEndTime,
                              @RequestParam(name = "exWorkDate",required = false) Date exWorkDate,
                              @RequestParam(name = "exStationId",required = false) String exStationId,
                              @RequestParam(name = "exGid",required = false) String exGid,
                              @RequestParam(name = "exPassType",required = false) Integer exPassType,
                              @RequestParam(name = "exShift",required = false) Integer exShift,
                              @RequestParam(name = "exVehPlate",required = false) String exVehPlate,
                              @RequestParam(name = "exCardId",required = false) String exCardId,
                              @RequestParam(name = "payWay",required = false) Integer payWay)
    {
        startPage();
        List<Map> info = transSummaryService.selectTransSummary(enStartTime,  enEndTime,  enWorkDate,  enStationId, passId, enGid,  enPassType,  enShift,  enVehPlate,  enCardId,  exStartTime,  exEndTime,  exWorkDate,  exStationId,  exGid,  exPassType,  exShift,  exVehPlate,  exCardId,  payWay);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:transSummary:export")
    @Log(title = "流水汇总", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestBody SummaryVo summaryVo)
    {
        List<TransSummaryVo> list = transSummaryService.selectTransSummaryList(summaryVo);
        ExcelUtil<TransSummaryVo> util = new ExcelUtil<TransSummaryVo>(TransSummaryVo.class);
        util.exportExcel(response,list, "流水汇总");
    }
}
