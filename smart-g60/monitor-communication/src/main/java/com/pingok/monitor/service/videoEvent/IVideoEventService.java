package com.pingok.monitor.service.videoEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.event.*;

public interface IVideoEventService {

    void fluxData(TblEventFlux tblEventFlux);

    void plateInfo(TblEventPlateInfo tblEventPlateInfo);

    void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent);

    void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow);

    void parkingEvent(TblEventParkingEvent tblEventParkingEvent);

    void updateEventVideo(Long ubiLogicId);

    void updateFluxData(TblEventFlux tblEventFlux);

    void updatePlateInfo(TblEventPlateInfo tblEventPlateInfo);

    void updateVehicleEvent(TblEventVehicleEvent tblEventVehicleEvent);

    void updatePassengerFlow(TblEventPassengerFlow tblEventPassengerFlow);

    void updateParkingEvent(TblEventParkingEvent tblEventParkingEvent);

}
