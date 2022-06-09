package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgPlantCarbonEmissionService;
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
@RequestMapping("/algPlantCarbonEmission")
@Api(value = "植物碳排放记录表", tags = "植物碳排放记录表")
public class TblAlgPlantCarbonEmissionController {

    @Resource
    private TblAlgPlantCarbonEmissionService algPlantCarbonEmissionService;

    @ApiOperation("植物碳排放记录表新增")
    @PostMapping("/")
    public AjaxResult save(@RequestBody TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        Boolean result = algPlantCarbonEmissionService.saveAlgPlantCarbonEmission(tblAlgPlantCarbonEmission);
        return AjaxResult.success(result);
    }

    @ApiOperation("植物碳排放记录表修改")
    @PutMapping("/")
    public AjaxResult modify(@RequestBody TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        Boolean result = algPlantCarbonEmissionService.modifyAlgPlantCarbonEmission(tblAlgPlantCarbonEmission);
        return AjaxResult.success(result);
    }
}
