package com.pingok.vocational.controller.parkingLot;

import com.pingok.vocational.service.parkingLot.ISmartToiletCubicleService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lal
 */
@RestController
@RequestMapping("/cubicleTotal")
public class SmartToiletCubicleController extends BaseController {

    @Autowired
    private ISmartToiletCubicleService iSmartToiletCubicleService;

    @GetMapping
    public AjaxResult getCubicleTotal(){
        Map map=new HashMap();
        map.put("nqBoy", iSmartToiletCubicleService.getToiletCubicleTotal().get(0));
        map.put("bqBoy", iSmartToiletCubicleService.getToiletCubicleTotal().get(1));
        map.put("bqGirl", iSmartToiletCubicleService.getToiletCubicleTotal().get(2));
        map.put("nqGirl", iSmartToiletCubicleService.getToiletCubicleTotal().get(3));
        return AjaxResult.success(map);
    }
}
