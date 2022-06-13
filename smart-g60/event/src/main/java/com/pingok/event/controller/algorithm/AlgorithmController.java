package com.pingok.event.controller.algorithm;

import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.algorithm.LaneAvgSpeed;
import com.pingok.event.service.algorithm.IAlgorithmService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author
 * @time 2022/6/8 9:30
 */
@Slf4j
@RestController
@RequestMapping("/algorithm")
public class AlgorithmController {

    @Autowired
    private IAlgorithmService iAlgorithmService;

    /* 根据桩号、车道号查询车道平均速度
    mileage 桩号(格式：[K、k]26+300)；laneNo 车道号(1，2，3，……)；dir 方向(1-上行，2-下行)
     */
    @PostMapping("/getLaneAvgSpeed")
    public AjaxResult getLaneAvgSpeed(@RequestBody JSONObject body) {
        return AjaxResult.success(iAlgorithmService.getLaneAvgSpeed(
                body.getString("mileage"), body.getInteger("laneNo"), body.getInteger("dir")));
    }
}
