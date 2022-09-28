package com.pingok.monitor.controller.videoEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.TblEventPassengerFlow;
import com.pingok.monitor.domain.event.TblEventPlateInfo;
import com.pingok.monitor.domain.event.TblEventVehicleEvent;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;

/**
 * @author
 * @time 2022/5/6 15:52
 */
@RestController
@Slf4j
@RequestMapping("/videoEvent")
public class VideoEventController extends BaseController {
    @Autowired
    private IVideoEventService iVideoEventService;

    @PostMapping
    public AjaxResult videoEvent(@RequestParam String type, @RequestBody JSONObject object) {
        String data = new String(Base64.getDecoder().decode(object.getString("data")));
        log.info("type----" + type);
        switch (type) {
//            case "FLUX":
//                TblEventFlux tblEventFlux = JSON.parseObject(data, TblEventFlux.class);
//                iVideoEventService.fluxData(tblEventFlux);
//                iVideoEventService.updateFluxData(tblEventFlux);
//                break;
            case "PLATE_INFO":
                TblEventPlateInfo tblEventPlateInfo = JSON.parseObject(data, TblEventPlateInfo.class);
                if (tblEventPlateInfo.getSzText().length() >= 5 && !tblEventPlateInfo.getSzText().equals("00000")&& !tblEventPlateInfo.getSzText().equals("000000")) {
                    iVideoEventService.plateInfo(tblEventPlateInfo);
                    iVideoEventService.updatePlateInfo(tblEventPlateInfo);
                }
                break;
            case "VEHICLE_EVENT":
                TblEventVehicleEvent tblEventVehicleEvent = JSON.parseObject(data, TblEventVehicleEvent.class);
                iVideoEventService.vehicleEvent(tblEventVehicleEvent);
                iVideoEventService.updateVehicleEvent(tblEventVehicleEvent);
                break;
            case "PASSENGER_FLOW":
                TblEventPassengerFlow tblEventPassengerFlow = JSON.parseObject(data, TblEventPassengerFlow.class);
                iVideoEventService.passengerFlow(tblEventPassengerFlow);
                iVideoEventService.updatePassengerFlow(tblEventPassengerFlow);
                break;
//            case "PARKING_EVENT":
//                TblEventParkingEvent tblEventParkingEvent = JSON.parseObject(data, TblEventParkingEvent.class);
//                iVideoEventService.parkingEvent(tblEventParkingEvent);
//                iVideoEventService.updateParkingEvent(tblEventParkingEvent);
//                break;
        }
        return AjaxResult.success();
    }
}
