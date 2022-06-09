package com.pingok.monitor.controller.videoEvent;

import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.*;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author
 * @time 2022/5/6 15:52
 */
@RestController
@RequestMapping("/videoEvent")
public class VideoEventController extends BaseController {
    @Autowired
    private IVideoEventService iVideoEventService;

    /**
     * 流量统计
     */
    @PostMapping("/flux")
    public AjaxResult fluxData(@RequestBody TblEventFlux tblEventFlux) {
        iVideoEventService.fluxData(tblEventFlux);
        iVideoEventService.updateFluxData(tblEventFlux);
        return AjaxResult.success();
    }

    /**
     * 过车数据
     */
    @PostMapping("/plateInfo")
    public AjaxResult plateInfo(@RequestBody TblEventPlateInfo tblEventPlateInfo) {
        iVideoEventService.plateInfo(tblEventPlateInfo);
        iVideoEventService.updatePlateInfo(tblEventPlateInfo);
        return AjaxResult.success();
    }

    /**
     * 交通事件
     */
    @PostMapping("/vehicleEvent")
    public AjaxResult vehicleEvent(@RequestBody TblEventVehicleEvent tblEventVehicleEvent) {
        iVideoEventService.vehicleEvent(tblEventVehicleEvent);
        iVideoEventService.updateVehicleEvent(tblEventVehicleEvent);
        return AjaxResult.success();
    }

    /**
     * 客流量
     */
    @PostMapping("/passengerFlow")
    public AjaxResult passengerFlow(@RequestBody TblEventPassengerFlow tblEventPassengerFlow) {
        iVideoEventService.passengerFlow(tblEventPassengerFlow);
        iVideoEventService.updatePassengerFlow(tblEventPassengerFlow);
        return AjaxResult.success();
    }

    /**
     * 货车检查事件
     */
    @PostMapping("/parkingEvent")
    public AjaxResult parkingEvent(@RequestBody TblEventParkingEvent tblEventParkingEvent) {
        iVideoEventService.parkingEvent(tblEventParkingEvent);
        iVideoEventService.updateParkingEvent(tblEventParkingEvent);
        return AjaxResult.success();
    }
}
