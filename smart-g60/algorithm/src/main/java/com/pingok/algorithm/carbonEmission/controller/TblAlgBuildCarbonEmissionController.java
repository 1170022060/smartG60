package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgBuildCarbonEmissionService;
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
@RequestMapping("/algBuildCarbonEmission")
@Api(value = "建筑碳排放记录表", tags = "建筑碳排放记录表")
public class TblAlgBuildCarbonEmissionController {

    @Resource
    private TblAlgBuildCarbonEmissionService algBuildCarbonEmissionService;

    @ApiOperation("建筑碳排放记录表新增")
    @PostMapping("/")
    public AjaxResult save(@RequestBody TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        Boolean result = algBuildCarbonEmissionService.saveAlgBuildCarbonEmission(tblAlgBuildCarbonEmission);
        return AjaxResult.success(result);
    }

    @ApiOperation("建筑碳排放记录表修改")
    @PutMapping("/")
    public AjaxResult modify(@RequestBody TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        Boolean result = algBuildCarbonEmissionService.modifyAlgBuildCarbonEmission(tblAlgBuildCarbonEmission);
        return AjaxResult.success(result);
    }
}
