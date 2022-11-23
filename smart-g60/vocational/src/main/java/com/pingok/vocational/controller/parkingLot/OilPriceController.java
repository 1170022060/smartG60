package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.pingok.vocational.service.parkingLot.IOilPriceService;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * 今日油价
 *
 * @author xia
 */
@RestController
@RequestMapping("/oilPrice")
public class OilPriceController extends BaseController {

    @Autowired
    private IOilPriceService iOilPriceService;

    @Log(title = "今日油价", businessType = BusinessType.OTHER)
    @GetMapping(value="/monitor")
    public AjaxResult monitor(@RequestParam(name = "date") Date date)
    {
        TblOilPrice info = iOilPriceService.selectOilPrice(date);
        return AjaxResult.success(info);
    }

    @Log(title = "今日油价-根据ID查询", businessType = BusinessType.OTHER)
    @GetMapping(value="/idInfo")
    public AjaxResult idInfo(@RequestParam(name = "id") Long id)
    {
        TblOilPrice idInfo = iOilPriceService.selectOilPriceById(id);
        return AjaxResult.success(idInfo);
    }

    @RequiresPermissions("vocational:oilPrice:add")
    @Log(title = "今日油价", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody TblOilPrice tblOilPrice)
    {
        if (UserConstants.NOT_UNIQUE.equals(iOilPriceService.checkDateUnique(tblOilPrice)))
        {
            return AjaxResult.error("新增日期'" + DateUtils.getTimeDay(tblOilPrice.getTransDate()) + "'失败，该日期已存在");
        }
        return toAjax(iOilPriceService.insertOilPrice(tblOilPrice));
    }

    @RequiresPermissions("vocational:oilPrice:edit")
    @Log(title = "今日油价", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TblOilPrice tblOilPrice)
    {
        if (UserConstants.NOT_UNIQUE.equals(iOilPriceService.checkDateUnique(tblOilPrice)))
        {
            return AjaxResult.error("修改日期'" + DateUtils.getTimeDay(tblOilPrice.getTransDate()) + "'失败，该日期已存在");
        }
        return toAjax(iOilPriceService.updateOilPrice(tblOilPrice));
    }
}
