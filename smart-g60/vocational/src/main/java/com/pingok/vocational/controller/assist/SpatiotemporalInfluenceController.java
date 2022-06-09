package com.pingok.vocational.controller.assist;

import com.pingok.vocational.service.assist.ISpatiotemporalInfluenceService;
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
 * 路段时空影响预估表
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/spatiotemporalInfluence")
public class SpatiotemporalInfluenceController extends BaseController {

    @Autowired
    private ISpatiotemporalInfluenceService spatiotemporalInfluenceService;

    @RequiresPermissions("vocational:spatiotemporalInfluence:info")
    @Log(title = "路段时空影响预估查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "eventType",required = false) String eventType,@RequestParam(name = "startTime",required = false) Date startTime, @RequestParam(name = "endTime",required = false) Date endTime)
    {
        startPage();
        List<Map> info = spatiotemporalInfluenceService.selectSpatiotemporal(eventType,startTime,endTime);
        return getDataTable(info);
    }
}
