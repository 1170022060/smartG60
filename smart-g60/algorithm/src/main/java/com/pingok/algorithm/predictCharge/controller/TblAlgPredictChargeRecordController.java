package com.pingok.algorithm.predictCharge.controller;

import com.pingok.algorithm.predictCharge.entity.TblAlgPredictChargeRecord;
import com.pingok.algorithm.predictCharge.service.TblAlgPredictChargeRecordService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author
 * @time 2022/09/05 16:30
 */
@RestController
@Slf4j
@RequestMapping("/predictCharge")
@Api(value = "收费预测", tags = "收费预测")
public class TblAlgPredictChargeRecordController {

    @Resource
    private TblAlgPredictChargeRecordService tblAlgPredictChargeRecordService;

    @ApiOperation("定时计算当天的收费预测记录")
    @GetMapping("/autoCurrentDayPredictCharge")
    public AjaxResult autoCurrentDayPredictCharge() throws Exception {
        tblAlgPredictChargeRecordService.autoCurrentDayPredictCharge();
        return AjaxResult.success();
    }

    @ApiOperation("输入日期范围预测收益")
    @GetMapping("/getPredictChargeRecordByDate")
    public AjaxResult getPredictChargeRecordByDate(String chargeIntervalId, String startDate, String endDate) {
        TblAlgPredictChargeRecord predictChargeRecord = tblAlgPredictChargeRecordService.getPredictChargeRecordByDate(chargeIntervalId, startDate, endDate);
        return AjaxResult.success(predictChargeRecord);
    }
}
