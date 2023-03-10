package com.pingok.event.service;

import com.pingok.event.domain.videoEvent.*;

public interface IVideoEventService {

    /**
     * 人像识别信息入库
     * @param tblFaceInfo
     */
    void faceInfo(TblFaceInfo tblFaceInfo);

    /**
     * 事件解除
     * @param tblEventVehicleEvent
     */
    void relieveEvent(TblEventVehicleEvent tblEventVehicleEvent);

    void fluxData(TblEventFlux tblEventFlux);

    void plateInfo(TblEventPlateInfo tblEventPlateInfo);

    void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent);

    void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow);

    void parkingEvent(TblEventParkingEvent tblEventParkingEvent);

    void parkVehInfo(TblEventPlateInfo tblEventPlateInfo);
    void parkingStatistics(TblEventPlateInfo tblEventPlateInfo);
}
