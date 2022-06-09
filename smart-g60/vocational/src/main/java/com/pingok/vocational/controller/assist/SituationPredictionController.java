package com.pingok.vocational.controller.assist;

import com.pingok.vocational.service.assist.ISituationPredictionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 路段运行态势预估表
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/situationPrediction")
public class SituationPredictionController extends BaseController {

    @Autowired
    private ISituationPredictionService situationPredictionService;

    @RequiresPermissions("vocational:situationPrediction:infoFlow")
    @Log(title = "路段运行态势-交通流量查询", businessType = BusinessType.OTHER)
    @GetMapping("/infoFlow")
    public TableDataInfo infoFlow( @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> infoFlow = situationPredictionService.selectSituationFlow(startDate,endDate);
        return getDataTable(infoFlow);
    }

    @RequiresPermissions("vocational:situationPrediction:infoStatus")
    @GetMapping("/infoStatus")
    @Log(title = "路段运行态势-交通状态查询", businessType = BusinessType.OTHER)
    public TableDataInfo infoStatus( @RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> infoStatus = situationPredictionService.selectSituationStatus(startDate,endDate);
        return getDataTable(infoStatus);
    }
}
