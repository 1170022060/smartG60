package com.pingok.vocational.controller.baseinfo;

import com.pingok.vocational.service.baseinfo.IRateService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 最小费率
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/rate")
public class RateController extends BaseController {

    @Autowired
    private IRateService rateService;

    @RequiresPermissions("vocational:rate:info")
    @Log(title = "最小费率-分页查询", businessType = BusinessType.OTHER)
    @GetMapping("/info")
    public TableDataInfo info(@RequestParam(name = "inStationName",required = false) String inStationName,@RequestParam(name = "exStationId",required = false) String exStationId
            , @RequestParam(name = "vehClass",required = false) Integer vehClass,@RequestParam(name = "versionNum",required = false) String versionNum)
    {
        startPage();
        List<Map> info = rateService.selectRate(inStationName,exStationId,vehClass,versionNum);
        return getDataTable(info);
    }

    @RequiresPermissions("vocational:rate:infoProv")
    @Log(title = "最小费率-分省明细查询", businessType = BusinessType.OTHER)
    @GetMapping("/infoProv")
    public TableDataInfo info(@RequestParam(name = "rateId") Long rateId)
    {
        startPage();
        List<Map> info = rateService.selectRateProv(rateId);
        return getDataTable(info);
    }

    @GetMapping(value = "/inStation")
    public AjaxResult inStation() {
        List<Map> info = rateService.selectInStation();
        return AjaxResult.success(info);
    }

    @GetMapping(value = "/versionNum")
    public AjaxResult versionNum() {
        List<Map> info = rateService.selectVersionNum();
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:rate:contrast")
    @Log(title = "最小费率-历史版本对比", businessType = BusinessType.OTHER)
    @GetMapping("/contrast")
    public TableDataInfo contrast(@RequestParam(name = "enID",required = false) String enID,@RequestParam(name = "exId",required = false) String exId, @RequestParam(name = "vehClass",required = false) Integer vehClass)
    {
        startPage();
        List<Map> info = rateService.selectRateContrast(enID,exId,vehClass);
        return getDataTable(info);
    }
}
