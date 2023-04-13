package com.pingok.monitor.controller.videoEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.TblEventPassengerFlow;
import com.pingok.monitor.domain.event.TblEventPlateInfo;
import com.pingok.monitor.domain.event.TblEventVehicleEvent;
import com.pingok.monitor.domain.event.vo.FaceInfoVo;
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
        switch (type) {
//            case "FLUX":
//                TblEventFlux tblEventFlux = JSON.parseObject(data, TblEventFlux.class);
//                iVideoEventService.fluxData(tblEventFlux);
//                iVideoEventService.updateFluxData(tblEventFlux);
//                break;
            case "PLATE_INFO":
                TblEventPlateInfo tblEventPlateInfo = JSON.parseObject(data, TblEventPlateInfo.class);
                log.info("PLATE_INFO-----" + tblEventPlateInfo.getSzText() + "------" + tblEventPlateInfo.getSzSourceCode());
                if (StringUtils.isCarNo(tblEventPlateInfo.getSzText())) {
                    iVideoEventService.plateInfo(tblEventPlateInfo);
                    iVideoEventService.updatePlateInfo(tblEventPlateInfo);
                }
                break;
            case "VEHICLE_EVENT":
                TblEventVehicleEvent tblEventVehicleEvent = JSON.parseObject(data, TblEventVehicleEvent.class);
                log.info("VEHICLE_EVENT-----" + tblEventVehicleEvent.getUiEventType());
                // 以下列表中配置的事件类型不会接收保存
                List<Integer> notAllowedList = Arrays.asList(3, 4, 5, 6, 8, 9, 10, 11, 12, 13, 17, 23, 24, 25, 26, 27, 28, 29, 33, 34, 35, 37, 38, 39, 40, 42, 43, 44);
                if (!notAllowedList.contains(tblEventVehicleEvent.getUiEventType())) {
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
            case "FACE_INFO":
                FaceInfoVo faceInfoVo = JSON.parseObject(data, FaceInfoVo.class);
                iVideoEventService.updateFaceInfo(faceInfoVo);
                break;
        }
        return AjaxResult.success();
    }
}
