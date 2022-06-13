package com.pingok.algorithm.event.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgPlantCarbonEmission;
import com.pingok.algorithm.event.entity.TblAlgEvent;
import com.pingok.algorithm.event.service.TblAlgEventService;
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
@RequestMapping("/algEvent")
@Api(value = "事件基础表", tags = "事件基础表")
public class TblAlgEventController extends BaseController {

    @Resource
    private TblAlgEventService algEventService;

    @ApiOperation("查询事件列表")
    @GetMapping("/list")
    public AjaxResult listByBean(TblAlgEvent tblAlgEvent) {
        List<TblAlgEvent> list = algEventService.listByBean(tblAlgEvent);
        return AjaxResult.success(list);
    }

    @ApiOperation("查询事件分页")
    @GetMapping("/page")
    public TableDataInfo pageByBean(TblAlgEvent tblAlgEvent) {
        startPage();
        List<TblAlgEvent> list = algEventService.listByBean(tblAlgEvent);
        return getDataTable(list);
    }

    @ApiOperation("查询事件详情")
    @GetMapping("/selectByBean")
    public AjaxResult selectByBean(TblAlgEvent tblAlgEvent) {
        TblAlgEvent result = algEventService.selectByBean(tblAlgEvent);
        return AjaxResult.success(result);
    }

    @ApiOperation("事件新增")
    @PostMapping("/")
    public AjaxResult save(@RequestBody TblAlgEvent tblAlgEvent) {
        Boolean result = algEventService.saveTblAlgEvent(tblAlgEvent);
        return AjaxResult.success(result);
    }

    @ApiOperation("事件修改")
    @PutMapping("/")
    public AjaxResult modify(@RequestBody TblAlgEvent tblAlgEvent) {
        Boolean result = algEventService.modifyTblAlgEvent(tblAlgEvent);
        return AjaxResult.success(result);
    }

    @ApiOperation("事件删除")
    @DeleteMapping("/{id}")
    public AjaxResult delete(@PathVariable("id") Long id) {
        Boolean result = algEventService.deleteTblAlgEvent(id);
        return AjaxResult.success(result);
    }
}
