package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.domain.parkingLot.TblOilPrice;
import com.pingok.vocational.service.parkingLot.IOilPriceService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
}
