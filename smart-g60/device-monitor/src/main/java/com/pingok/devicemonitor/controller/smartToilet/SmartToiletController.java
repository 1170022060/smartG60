package com.pingok.devicemonitor.controller.smartToilet;

import com.alibaba.fastjson.JSONObject;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletCubicle;
import com.pingok.devicemonitor.domain.smartToilet.TblSmartToiletInfo;
import com.pingok.devicemonitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 智慧厕所 信息操作处理
 *
 * @author qiumin
 */
@RestController
@RequestMapping("/smartToilet")
public class SmartToiletController extends BaseController {
    @Autowired
    private ISmartToiletService iSmartToiletService;


    @PostMapping("/marqueeText")
    public AjaxResult marqueeText() {
        iSmartToiletService.marqueeText();
        return AjaxResult.success();
    }

    @PostMapping("/weather")
    public AjaxResult weather() {
        iSmartToiletService.weather();
        return AjaxResult.success();
    }

    @PostMapping("/timeCalibration")
    public AjaxResult timeCalibration() {
        iSmartToiletService.timeCalibration();
        return AjaxResult.success();
    }

    @PostMapping
    public AjaxResult sensorData(@RequestBody JSONObject sensorData) {
        iSmartToiletService.sensorData(sensorData);
        return AjaxResult.success();
    }

    /**
     * 更新厕所坑位状态
     * @param tblSmartToiletCubicle
     * @return
     */
    @RequiresPermissions("devicemonitor:smartToilet:edit")
    @Log(title = "更新厕所坑位状态",businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult update(@Validated @RequestBody TblSmartToiletCubicle tblSmartToiletCubicle){
        TblSmartToiletCubicle cubicle = iSmartToiletService.updateToiletStatus(tblSmartToiletCubicle);
        TblSmartToiletInfo info = iSmartToiletService.selectById(cubicle.getSerId());
        iSmartToiletService.setSensor(info.getSerNum(),cubicle.getIndexId(),cubicle.getStatus());
        return AjaxResult.success();
    }
}
