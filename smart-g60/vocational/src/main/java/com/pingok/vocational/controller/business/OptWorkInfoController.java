package com.pingok.vocational.controller.business;

import com.pingok.vocational.service.business.IOptWorkInfoService;
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
 * 操作员工班信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/optWork")
public class OptWorkInfoController extends BaseController {

    @Autowired
    private IOptWorkInfoService optWorkInfoService;

    @RequiresPermissions("vocational:optWork:info")
    @Log(title = "操作员工班信息-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "startDate",required = false) Date startDate, @RequestParam(name = "endDate",required = false) Date endDate,@RequestParam(name = "stationId",required = false) String stationId,@RequestParam(name = "optName",required = false) String optName,@RequestParam(name = "shift",required = false) Integer shift)
    {
        startPage();
        List<Map> info = optWorkInfoService.selectOptWorkInfo(startDate,  endDate,  stationId,  optName,  shift);
        return getDataTable(info);
    }
}
