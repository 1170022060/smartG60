package com.pingok.vocational.controller.device;

import com.pingok.vocational.domain.device.TblGantryInfoDtl;
import com.pingok.vocational.domain.device.vo.DtlEnum;
import com.pingok.vocational.service.device.IGantryInfoDtlService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门架设备详情
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/deviceGantryDtl")
public class GantryInfoDtlController extends BaseController {

    @Autowired
    private IGantryInfoDtlService gantryInfoDtlService;

    @RequiresPermissions("vocational:deviceGantryDtl:info")
    @Log(title = "门架设备详情查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/info")
    public AjaxResult info(@RequestParam(name = "infoId") Long infoId)
    {
        List<TblGantryInfoDtl> info = gantryInfoDtlService.selectGantryInfoDtl(infoId);
        return AjaxResult.success(info);
    }

    @RequiresPermissions("vocational:deviceGantryDtl:add")
    @Transactional
    @Log(title = "门架设备详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody DtlEnum dtlEnum)
    {
        return toAjax(gantryInfoDtlService.insertGantryInfoDtl(dtlEnum));
    }
}
