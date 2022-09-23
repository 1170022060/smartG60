package com.pingok.algorithm.carbonEmission.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.carbonEmission.entity.TblAlgVehCarbonEmission;
import com.pingok.algorithm.carbonEmission.service.TblAlgBuildCarbonEmissionService;
import com.pingok.algorithm.carbonEmission.service.TblAlgVehCarbonEmissionService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.core.utils.PageUtils.startPage;

/**
 * @author
 * @time 2022/05/06 16:30
 */
@RestController
@Slf4j
@RequestMapping("/algBuildCarbonEmission")
@Api(value = "建筑碳排放记录表", tags = "建筑碳排放记录表")
public class TblAlgBuildCarbonEmissionController extends BaseController {

    @Resource
    private TblAlgBuildCarbonEmissionService algBuildCarbonEmissionService;

    @ApiOperation("查询建筑碳排放列表")
    @GetMapping("/list")
    public AjaxResult listByBean(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        List<TblAlgBuildCarbonEmission> list = algBuildCarbonEmissionService.listByBean(tblAlgBuildCarbonEmission);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询建筑碳排放分页")
    @GetMapping("/page")
    public TableDataInfo pageByBean(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        startPage();
        List<TblAlgBuildCarbonEmission> list = algBuildCarbonEmissionService.listByBean(tblAlgBuildCarbonEmission);
        return getDataTable(list);
    }

    @ApiOperation("查询建筑碳排放详情")
    @GetMapping("/selectByBean")
    public AjaxResult selectByBean(TblAlgBuildCarbonEmission tblAlgBuildCarbonEmission) {
        TblAlgBuildCarbonEmission result = algBuildCarbonEmissionService.selectByBean(tblAlgBuildCarbonEmission);
        return AjaxResult.success(result);
    }

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
