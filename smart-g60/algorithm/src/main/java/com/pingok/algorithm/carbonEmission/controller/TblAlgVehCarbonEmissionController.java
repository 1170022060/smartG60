package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgVehCarbonEmissionService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author
 * @time 2022/05/06 16:30
 */
@RestController
@Slf4j
@RequestMapping("/algVehCarbonEmission")
@Api(value = "车辆碳排放记录表", tags = "车辆碳排放记录表")
public class TblAlgVehCarbonEmissionController {

    @Resource
    private TblAlgVehCarbonEmissionService algVehCarbonEmissionService;

    @GetMapping("/getAlgVehCarbonEmission")
    public AjaxResult getAlgVehCarbonEmission(String queryTime) {
        TblAlgVehCarbonEmission result = algVehCarbonEmissionService.getAlgVehCarbonEmission(queryTime);
        return AjaxResult.success(result);
    }

    @ApiOperation("植物碳排放记录表新增")
    @PostMapping("/")
    public AjaxResult save() {
        algVehCarbonEmissionService.saveAlgVehCarbonEmission();
        return AjaxResult.success();
    }
}
