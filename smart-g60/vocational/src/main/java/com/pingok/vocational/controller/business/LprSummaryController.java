package com.pingok.vocational.controller.business;

import com.pingok.vocational.domain.business.vo.LprSummaryVo;
import com.pingok.vocational.domain.business.vo.SummaryVo;
import com.pingok.vocational.service.business.ILprSummaryService;
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
 * 车道牌识
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/lprSummary")
public class LprSummaryController extends BaseController {
    @Autowired
    private ILprSummaryService lprSummaryService;
//
//    @RequiresPermissions("vocational:lprSummary:info")
    @Log(title = "入口车道牌识-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/enInfo")
    public TableDataInfo enInfo(@RequestParam(name = "enStartTime",required = false) Date enStartTime,
                              @RequestParam(name = "enEndTime",required = false) Date enEndTime,
                              @RequestParam(name = "enStationId",required = false) String enStationId,
                              @RequestParam(name = "enLaneType",required = false) Integer enLaneType,
                              @RequestParam(name = "enVehPlate",required = false) String enVehPlate)
    {
        startPage();
        List<Map> info = lprSummaryService.selectEnLprTrans(enStartTime, enEndTime, enStationId, enLaneType, enVehPlate);
        return getDataTable(info);
    }
//
//    @RequiresPermissions("vocational:lprSummary:info")
    @Log(title = "出口车道牌识-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/exInfo")
    public TableDataInfo exInfo(@RequestParam(name = "exStartTime",required = false) Date exStartTime,
                              @RequestParam(name = "exEndTime",required = false) Date exEndTime,
                              @RequestParam(name = "exStationId",required = false) String exStationId,
                              @RequestParam(name = "exLaneType",required = false) Integer exLaneType,
                              @RequestParam(name = "exVehPlate",required = false) String exVehPlate)
    {
        startPage();
        List<Map> info = lprSummaryService.selectExLprTrans(exStartTime, exEndTime, exStationId, exLaneType, exVehPlate);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:lprSummary:export")
    @Log(title = "车道牌识", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public void export(HttpServletResponse response, @RequestBody SummaryVo summaryVo)
    {
        List<LprSummaryVo> list = lprSummaryService.selectLprTransList(summaryVo);
        ExcelUtil<LprSummaryVo> util = new ExcelUtil<LprSummaryVo>(LprSummaryVo.class);
        util.exportExcel(response,list, "车道牌识");
    }
}
