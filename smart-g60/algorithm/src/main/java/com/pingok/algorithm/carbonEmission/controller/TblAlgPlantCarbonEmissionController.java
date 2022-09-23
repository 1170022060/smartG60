package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgPlantCarbonEmissionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @time 2022/05/06 16:30
 */
@RestController
@Slf4j
@RequestMapping("/algPlantCarbonEmission")
@Api(value = "植物碳排放记录表", tags = "植物碳排放记录表")
public class TblAlgPlantCarbonEmissionController extends BaseController {

    @Resource
    private TblAlgPlantCarbonEmissionService algPlantCarbonEmissionService;

    @ApiOperation("查询植物碳排放列表")
    @GetMapping("/list")
    public AjaxResult listByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        List<TblAlgPlantCarbonEmission> list = algPlantCarbonEmissionService.listByBean(tblAlgPlantCarbonEmission);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询植物碳排放分页")
    @GetMapping("/page")
    public TableDataInfo pageByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        startPage();
        List<TblAlgPlantCarbonEmission> list = algPlantCarbonEmissionService.listByBean(tblAlgPlantCarbonEmission);
        return getDataTable(list);
    }

    @ApiOperation("查询植物碳排放详情")
    @GetMapping("/selectByBean")
    public AjaxResult selectByBean(TblAlgPlantCarbonEmission tblAlgPlantCarbonEmission) {
        TblAlgPlantCarbonEmission result = algPlantCarbonEmissionService.selectByBean(tblAlgPlantCarbonEmission);
        return AjaxResult.success(result);
    }

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
