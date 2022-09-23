package com.pingok.algorithm.event.controller;

import com.pingok.algorithm.event.entity.TblAlgEvent;
import com.pingok.algorithm.event.entity.TblAlgEventEffectStatus;
import com.pingok.algorithm.event.service.TblAlgEventEffectStatusService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @time 2022/06/07 10:30
 */
@RestController
@Slf4j
@RequestMapping("/algEventEffectStatus")
@Api(value = "事件影响状态表", tags = "事件影响状态表")
public class TblAlgEventEffectStatusController extends BaseController {

    @Resource
    private TblAlgEventEffectStatusService algEventEffectStatusService;

    @ApiOperation("查询事件影响状态列表")
    @GetMapping("/list")
    public AjaxResult listByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        List<TblAlgEventEffectStatus> list = algEventEffectStatusService.listByBean(tblAlgEventEffectStatus);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询最新事件影响状态列表")
    @GetMapping("/selectEventEffectStatusListByBean")
    public AjaxResult selectEventEffectStatusListByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        List<TblAlgEventEffectStatus> list = algEventEffectStatusService.selectEventEffectStatusListByBean(tblAlgEventEffectStatus);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询事件影响状态分页")
    @GetMapping("/page")
    public TableDataInfo pageByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        startPage();
        List<TblAlgEventEffectStatus> list = algEventEffectStatusService.listByBean(tblAlgEventEffectStatus);
        return getDataTable(list);
    }

    @ApiOperation("查询事件影响状态详情")
    @GetMapping("/selectByBean")
    public AjaxResult selectByBean(TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        TblAlgEventEffectStatus result = algEventEffectStatusService.selectByBean(tblAlgEventEffectStatus);
        return AjaxResult.success(result);
    }

    @ApiOperation("事件影响状态新增")
    @PostMapping("/")
    public AjaxResult save(@RequestBody TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        Boolean result = algEventEffectStatusService.saveTblAlgEventEffectStatus(tblAlgEventEffectStatus);
        return AjaxResult.success(result);
    }

    @ApiOperation("自动保存事件影响状态记录")
    @PostMapping("/autoSaveEventEffectStatus")
    public AjaxResult autoSaveEventEffectStatus() {
        Boolean result = algEventEffectStatusService.autoSaveEventEffectStatus();
        return AjaxResult.success(result);
    }

    @ApiOperation("事件影响状态修改")
    @PutMapping("/")
    public AjaxResult modify(@RequestBody TblAlgEventEffectStatus tblAlgEventEffectStatus) {
        Boolean result = algEventEffectStatusService.modifyTblAlgEventEffectStatus(tblAlgEventEffectStatus);
        return AjaxResult.success(result);
    }

    @ApiOperation("事件影响状态删除")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        Boolean result = algEventEffectStatusService.deleteTblAlgEventEffectStatus(id);
        return AjaxResult.success(result);
    }
}
