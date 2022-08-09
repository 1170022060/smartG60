package com.pingok.monitor.controller.videoEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.*;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author
 * @time 2022/5/6 15:52
 */
@RestController
@RequestMapping("/videoEvent")
public class VideoEventController extends BaseController {
    @Autowired
    private IVideoEventService iVideoEventService;

    @PostMapping
    public AjaxResult videoEvent(@RequestParam String type, @RequestBody JSONObject object) {
        switch (type) {
            case "FLUX":
                TblEventFlux tblEventFlux = JSON.parseObject(object.toJSONString(), TblEventFlux.class);
                iVideoEventService.fluxData(tblEventFlux);
                iVideoEventService.updateFluxData(tblEventFlux);
                break;
            case "PLATE_INFO":
                TblEventPlateInfo tblEventPlateInfo = JSON.parseObject(object.toJSONString(), TblEventPlateInfo.class);
                iVideoEventService.plateInfo(tblEventPlateInfo);
                iVideoEventService.updatePlateInfo(tblEventPlateInfo);
                break;
            case "VEHICLE_EVENT":
                TblEventVehicleEvent tblEventVehicleEvent = JSON.parseObject(object.toJSONString(), TblEventVehicleEvent.class);
                iVideoEventService.vehicleEvent(tblEventVehicleEvent);
                iVideoEventService.updateVehicleEvent(tblEventVehicleEvent);
                break;
            case "PASSENGER_FLOW":
                TblEventPassengerFlow tblEventPassengerFlow = JSON.parseObject(object.toJSONString(), TblEventPassengerFlow.class);
                iVideoEventService.passengerFlow(tblEventPassengerFlow);
                iVideoEventService.updatePassengerFlow(tblEventPassengerFlow);
                break;
            case "PARKING_EVENT":
                TblEventParkingEvent tblEventParkingEvent = JSON.parseObject(object.toJSONString(), TblEventParkingEvent.class);
                iVideoEventService.parkingEvent(tblEventParkingEvent);
                iVideoEventService.updateParkingEvent(tblEventParkingEvent);
                break;
        }
        return AjaxResult.success();
    }

}
