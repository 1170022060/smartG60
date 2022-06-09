package com.pingok.event.service.impl;

import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.domain.videoEvent.*;
import com.pingok.event.mapper.videoEvent.*;
import com.pingok.event.service.IEventService;
import com.pingok.event.service.IVideoEventService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author
 * @time 2022/5/6 21:28
 */
@Slf4j
@Service
public class VideoEventServiceImpl implements IVideoEventService {

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblEventFluxMapper tblEventFluxMapper;
    @Autowired
    private TblEventParkingEventMapper tblEventParkingEventMapper;
    @Autowired
    private TblEventPassengerFlowMapper tblEventPassengerFlowMapper;
    @Autowired
    private TblEventVehicleEventMapper tblEventVehicleEventMapper;
    @Autowired
    private TblEventPlateInfoMapper tblEventPlateInfoMapper;
    @Autowired
    private IEventService iEventService;
    @Autowired
    private RedisService redisService;


    @Override
    public void fluxData(TblEventFlux tblEventFlux) {
        Example example = new Example(TblEventFlux.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventFlux.getUbiLogicId());
        TblEventFlux eventFlux = tblEventFluxMapper.selectOneByExample(example);
        if (eventFlux == null) {
            eventFlux = new TblEventFlux();
            BeanUtils.copyNotNullProperties(tblEventFlux, eventFlux);
            eventFlux.setId(remoteIdProducerService.nextId());
            tblEventFluxMapper.insert(eventFlux);
        } else {
            BeanUtils.copyNotNullProperties(tblEventFlux, eventFlux);
            tblEventFluxMapper.updateByPrimaryKey(eventFlux);
        }
    }

    @Override
    public void plateInfo(TblEventPlateInfo tblEventPlateInfo) {
        Example example = new Example(TblEventPlateInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventPlateInfo.getUbiLogicId());
        TblEventPlateInfo eventPlateInfo = tblEventPlateInfoMapper.selectOneByExample(example);
        if (eventPlateInfo == null) {
            eventPlateInfo = new TblEventPlateInfo();
            BeanUtils.copyNotNullProperties(tblEventPlateInfo, eventPlateInfo);
            eventPlateInfo.setId(remoteIdProducerService.nextId());
            tblEventPlateInfoMapper.insert(eventPlateInfo);
        } else {
            BeanUtils.copyNotNullProperties(tblEventPlateInfo, eventPlateInfo);
            tblEventPlateInfoMapper.updateByPrimaryKey(eventPlateInfo);
        }
    }

    @Override
    public void vehicleEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        Example example = new Example(TblEventVehicleEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventVehicleEvent.getUbiLogicId());
        TblEventVehicleEvent eventVehicleEvent = tblEventVehicleEventMapper.selectOneByExample(example);
        if (eventVehicleEvent == null) {
            eventVehicleEvent = new TblEventVehicleEvent();
            BeanUtils.copyNotNullProperties(tblEventVehicleEvent, eventVehicleEvent);
            eventVehicleEvent.setId(remoteIdProducerService.nextId());
            tblEventVehicleEventMapper.insert(eventVehicleEvent);

            TblEventRecord eventRecord = new TblEventRecord();
            eventRecord.setEventType(eventVehicleEvent.getUiEventType().toString());
            eventRecord.setVehClass(eventVehicleEvent.getUiVehicleTypeDetail());
            eventRecord.setVehColor(eventVehicleEvent.getUiVehiclePlateColor());
            eventRecord.setEventPhoto(eventVehicleEvent.getSzImg());
            eventRecord.setEventTime(new Date(eventVehicleEvent.getUbiTime()));
            eventRecord.setSpeed(eventVehicleEvent.getUiVehicleSpeed());
            eventRecord.setLane(eventVehicleEvent.getUiVehicleLane());
            eventRecord.setEventId(eventVehicleEvent.getUbiLogicId());
            eventRecord.setCreateTime(DateUtils.getNowDate());
            eventRecord.setStatus(0);
            if (eventVehicleEvent.getUbiSourceId() != null) {
                Object o = tblEventVehicleEventMapper.findByDeviceId(eventVehicleEvent.getUbiSourceId().toString());
                if (o != null) {
                    eventRecord.setLocationInterval(redisService.geoGet("sxj", o));
                }
            }
            iEventService.insert(eventRecord);
        } else {
            BeanUtils.copyNotNullProperties(tblEventVehicleEvent, eventVehicleEvent);
            tblEventVehicleEventMapper.updateByPrimaryKey(eventVehicleEvent);
        }
    }

    @Override
    public void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        Example example = new Example(TblEventPassengerFlow.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("uiType", tblEventPassengerFlow.getUiType());
        TblEventPassengerFlow eventPassengerFlow = tblEventPassengerFlowMapper.selectOneByExample(example);
        if (eventPassengerFlow == null) {
            eventPassengerFlow = new TblEventPassengerFlow();
            BeanUtils.copyNotNullProperties(tblEventPassengerFlow, eventPassengerFlow);
            eventPassengerFlow.setId(remoteIdProducerService.nextId());
            tblEventPassengerFlowMapper.insert(eventPassengerFlow);
        } else {
            BeanUtils.copyNotNullProperties(tblEventPassengerFlow, eventPassengerFlow);
            tblEventPassengerFlowMapper.updateByPrimaryKey(eventPassengerFlow);
        }
    }

    @Override
    public void parkingEvent(TblEventParkingEvent tblEventParkingEvent) {
        Example example = new Example(TblEventParkingEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventParkingEvent.getUbiLogicId());
        TblEventParkingEvent eventParkingEvent = tblEventParkingEventMapper.selectOneByExample(example);
        if (eventParkingEvent == null) {
            eventParkingEvent = new TblEventParkingEvent();
            BeanUtils.copyNotNullProperties(tblEventParkingEvent, eventParkingEvent);
            eventParkingEvent.setId(remoteIdProducerService.nextId());
            tblEventParkingEventMapper.insert(eventParkingEvent);
        } else {
            BeanUtils.copyNotNullProperties(tblEventParkingEvent, eventParkingEvent);
            tblEventParkingEventMapper.updateByPrimaryKey(eventParkingEvent);
        }
    }
}
