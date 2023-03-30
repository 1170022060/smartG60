package com.pingok.vocational.controller.report;

import com.pingok.vocational.service.report.IVdHistoryRecordService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/vd")
public class VdHistoryRecordController extends BaseController {

    @Autowired
    private IVdHistoryRecordService iVdHistoryRecordService;

    @GetMapping("/vdFlowStatis")
    public TableDataInfo vdFlowStatis(@RequestParam(name = "deviceName",required = false) String deviceName,
                                      @RequestParam(name = "statisticsType",required = false) Integer statisticsType,
                                      @RequestParam(name = "startDate",required = false) Date startDate,
                                      @RequestParam(name = "endDate",required = false) Date endDate){
        startPage();
        List<Map> info = iVdHistoryRecordService.selectVdHistory(deviceName,statisticsType,startDate,endDate);
        return getDataTable(info);
    }

    @GetMapping("/pileNo")
    public AjaxResult getPileNo(){
        return AjaxResult.success(iVdHistoryRecordService.selectPileNo());
    }
}
