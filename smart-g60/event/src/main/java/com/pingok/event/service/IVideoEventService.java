package com.pingok.event.service;

import com.pingok.event.domain.videoEvent.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

    /**
     * 查询未戴口罩详情
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map> searchNoMask(Date startTime, Date endTime);
}
