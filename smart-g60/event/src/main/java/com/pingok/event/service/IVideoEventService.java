package com.pingok.event.service;

import com.pingok.event.domain.videoEvent.*;

public interface IVideoEventService {


    void fluxData(TblEventFlux tblEventFlux);

    void plateInfo(TblEventPlateInfo tblEventPlateInfo);

    void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent);

    void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow);

    void parkingEvent(TblEventParkingEvent tblEventParkingEvent);

    void parkVehInfo(TblEventPlateInfo tblEventPlateInfo);
    void parkingStatistics(TblEventPlateInfo tblEventPlateInfo);
}
