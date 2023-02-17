package com.pingok.monitor.controller.videoEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.TblEventPassengerFlow;
import com.pingok.monitor.domain.event.TblEventPlateInfo;
import com.pingok.monitor.domain.event.TblEventVehicleEvent;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.pingok.monitor.service.videoEvent.IVideoService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;

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
    @Autowired
    private IVideoService iVideoService;

    @GetMapping
    public AjaxResult getVideo(Long ubiLogicId) {
        iVideoEventService.updateEventVideo(ubiLogicId);
        return AjaxResult.success();
    }


    @PostMapping
    public AjaxResult videoEvent(@RequestParam String type, @RequestBody JSONObject object) {
        String data = new String(Base64.getDecoder().decode(object.getString("data")));
        log.info("type----" + type + "-----data----" + data);
        switch (type) {
//            case "FLUX":
//                TblEventFlux tblEventFlux = JSON.parseObject(data, TblEventFlux.class);
//                iVideoEventService.fluxData(tblEventFlux);
//                iVideoEventService.updateFluxData(tblEventFlux);
//                break;
            case "PLATE_INFO":
                TblEventPlateInfo tblEventPlateInfo = JSON.parseObject(data, TblEventPlateInfo.class);
                if (StringUtils.isCarNo(tblEventPlateInfo.getSzText())) {
                    iVideoEventService.plateInfo(tblEventPlateInfo);
                    iVideoEventService.updatePlateInfo(tblEventPlateInfo);
                }
                break;
            case "VEHICLE_EVENT":
                TblEventVehicleEvent tblEventVehicleEvent = JSON.parseObject(data, TblEventVehicleEvent.class);
                List<Integer> list = Arrays.asList(5, 6, 14, 15, 17, 10016, 37);
                if (!list.contains(tblEventVehicleEvent.getUiEventType())) {
                    iVideoEventService.vehicleEvent(tblEventVehicleEvent);
                    iVideoEventService.updateVehicleEvent(tblEventVehicleEvent);
//                    list = Arrays.asList(31, 32, 34, 35, 36, 40, 41);
//                    if (!list.contains(tblEventVehicleEvent.getUiEventType())) {
//                        iVideoService.linkage(tblEventVehicleEvent.getUbiLogicId());
//                    }
                }
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
