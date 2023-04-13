package com.pingok.dingtalk.controller.base;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;
import com.pingok.dingtalk.daemon.base.DeviceInfo;
import com.pingok.dingtalk.service.base.IDeviceInfoService;

import java.util.List;

import javax.annotation.Resource;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.core.web.controller.BaseController;

/**
 * 设施设备信息表-钉钉 控制器
 *
 * @author chenwg
 * @since 2023-04-13
 */
@RestController
@RequestMapping("/device-info")
@Api(value = "设施设备信息表-钉钉", tags = "设施设备信息表-钉钉")
@Slf4j
public class DeviceInfoController extends BaseController {

    @Resource
    private IDeviceInfoService iDeviceInfoService;

    @ApiOperation("设施设备信息表-钉钉分页列表")
    @GetMapping("/page")
    public TableDataInfo page(DeviceInfo deviceInfo) {
        startPage();
        List<DeviceInfo> pageList = iDeviceInfoService.listByEntity(deviceInfo);
        return getDataTable(pageList);
    }

    @ApiOperation("设施设备信息表-钉钉列表")
    @GetMapping("/list")
    public AjaxResult list(DeviceInfo deviceInfo) {
        List<DeviceInfo> list = iDeviceInfoService.listByEntity(deviceInfo);
        return AjaxResult.success(list);
    }

    @ApiOperation("设施设备信息表-钉钉详情")
    @GetMapping(value = "/{id}")
    public AjaxResult selectById(@PathVariable("id") Short id) {
        DeviceInfo deviceInfo = iDeviceInfoService.selectById(id);
        return AjaxResult.success(deviceInfo);
    }

    @ApiOperation("设施设备信息表-钉钉新增")
    @PostMapping("")
    public AjaxResult add(@RequestBody DeviceInfo deviceInfo) {
        return toAjax(iDeviceInfoService.saveDeviceInfo(deviceInfo));
    }

    @ApiOperation("设施设备信息表-钉钉修改")
    @PutMapping("")
    public AjaxResult modify(@RequestBody DeviceInfo deviceInfo) {
        return toAjax(iDeviceInfoService.modify(deviceInfo));
    }

    @ApiOperation("设施设备信息表-钉钉删除(单个条目)")
    @DeleteMapping(value = "/{id}")
    public AjaxResult deleteById(@PathVariable("id") Short id) {
        return toAjax(iDeviceInfoService.deleteById(id));
    }
}
