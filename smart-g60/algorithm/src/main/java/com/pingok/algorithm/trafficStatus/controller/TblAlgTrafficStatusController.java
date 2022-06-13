package com.pingok.algorithm.trafficStatus.controller;

import com.pingok.algorithm.carbonEmission.entity.TblAlgBuildCarbonEmission;
import com.pingok.algorithm.trafficStatus.entity.TblAlgTrafficStatus;
import com.pingok.algorithm.trafficStatus.service.TblAlgTrafficStatusService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author
 * @time 2022/05/11 16:30
 */
@RestController
@Slf4j
@RequestMapping("/algTrafficStatus")
@Api(value = "交通状态记录表", tags = "交通状态记录表")
public class TblAlgTrafficStatusController extends BaseController {

    @Resource
    private TblAlgTrafficStatusService trafficStatusService;

    @ApiOperation("查询最新交通状态列表")
    @GetMapping("/list")
    public AjaxResult list(TblAlgTrafficStatus tblAlgTrafficStatus){
        List<TblAlgTrafficStatus> list = trafficStatusService.listByBean(tblAlgTrafficStatus);
        return AjaxResult.success(list);
    }

    @ApiOperation("交通状态记录表新增")
    @PostMapping("/")
    public AjaxResult save(Integer direction) throws Exception {
        trafficStatusService.saveTrafficStatusService(direction);
        return AjaxResult.success();
    }
}
