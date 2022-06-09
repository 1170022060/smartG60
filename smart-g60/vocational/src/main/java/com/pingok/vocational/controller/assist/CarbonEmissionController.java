package com.pingok.vocational.controller.assist;

import com.pingok.vocational.service.assist.ICarbonEmissionService;
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
 * 碳排放统计
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/carbonEmission")
public class CarbonEmissionController extends BaseController{

    @Autowired
    private ICarbonEmissionService carbonEmissionService;

    @RequiresPermissions("vocational:carbonEmission:info")
    @Log(title = "碳排放统计查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate)
    {
        startPage();
        List<Map> info = carbonEmissionService.selectCarbonEmission(startDate,endDate);
        return getDataTable(info);
    }
}
