package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgVehCarbonEmissionService;
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
@RequestMapping("/algVehCarbonEmission")
@Api(value = "车辆碳排放记录表", tags = "车辆碳排放记录表")
public class TblAlgVehCarbonEmissionController extends BaseController {

    @Resource
    private TblAlgVehCarbonEmissionService algVehCarbonEmissionService;

    @GetMapping("/getAlgVehCarbonEmission")
    @ApiOperation("按时间查询车辆碳排放")
    public AjaxResult getAlgVehCarbonEmission(String queryTime) {
        TblAlgVehCarbonEmission result = algVehCarbonEmissionService.getAlgVehCarbonEmission(queryTime);
        return AjaxResult.success(result);
    }

    @GetMapping("/list")
    @ApiOperation("查询车辆碳排放列表")
    public AjaxResult listByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission) {
        List<TblAlgVehCarbonEmission> list = algVehCarbonEmissionService.listByBean(tblAlgVehCarbonEmission);
        return AjaxResult.success(list);
    }

    @GetMapping("/page")
    @ApiOperation("查询车辆碳排放分页")
    public TableDataInfo pageByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission) {
        startPage();
        List<TblAlgVehCarbonEmission> list = algVehCarbonEmissionService.listByBean(tblAlgVehCarbonEmission);
        return getDataTable(list);
    }

    @GetMapping("/selectByBean")
    @ApiOperation("查询车辆碳排放详情")
    public AjaxResult selectByBean(TblAlgVehCarbonEmission tblAlgVehCarbonEmission) {
        TblAlgVehCarbonEmission result = algVehCarbonEmissionService.selectByBean(tblAlgVehCarbonEmission);
        return AjaxResult.success(result);
    }

    @ApiOperation("植物碳排放记录表新增")
    @PostMapping("/")
    public AjaxResult save() {
        algVehCarbonEmissionService.saveAlgVehCarbonEmission();
        return AjaxResult.success();
    }
}
