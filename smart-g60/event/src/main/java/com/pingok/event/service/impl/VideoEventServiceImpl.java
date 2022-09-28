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

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    private TblParkingVehicleInfoMapper tblParkingVehicleInfoMapper;
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
        List<Long> in = Arrays.asList(1L, 2L, 3L, 4L);
        if (in.contains(tblEventPlateInfo.getUbiSourceId())) {
            tblEventPlateInfo.setId(remoteIdProducerService.nextId());
            tblEventPlateInfoMapper.insert(tblEventPlateInfo);
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
        tblEventPassengerFlow.setId(remoteIdProducerService.nextId());
        tblEventPassengerFlowMapper.insert(tblEventPassengerFlow);
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

    @Override
    public void parkVehInfo(TblEventPlateInfo tblEventPlateInfo) {
        List<Long> in = Arrays.asList(1L, 2L, 3L, 4L);
        List<Long> out = Arrays.asList(5L, 6L, 7L, 8L);
        if (in.contains(tblEventPlateInfo.getUbiSourceId())) {
            TblParkingVehicleInfo tblParkingVehicleInfo = new TblParkingVehicleInfo();
            tblParkingVehicleInfo.setId(remoteIdProducerService.nextId());
            tblParkingVehicleInfo.setEnTime(new Date(tblEventPlateInfo.getUbiTime()));
            tblParkingVehicleInfo.setVehPlate(tblEventPlateInfo.getSzText());
            tblParkingVehicleInfo.setVehClass(tblEventPlateInfo.getUiStatType());
            tblParkingVehicleInfo.setVehClassSub(tblEventPlateInfo.getUiSubType());
            tblParkingVehicleInfo.setVehColor(tblEventPlateInfo.getUiColor());
            if (tblEventPlateInfo.getUbiSourceId() == 1L) {
                tblParkingVehicleInfo.setParkingId(1L);
            } else if (tblEventPlateInfo.getUbiSourceId() == 2L) {
                tblParkingVehicleInfo.setParkingId(2L);
            } else if (tblEventPlateInfo.getUbiSourceId() == 3L) {
                tblParkingVehicleInfo.setParkingId(3L);
            } else if (tblEventPlateInfo.getUbiSourceId() == 4L) {
                tblParkingVehicleInfo.setParkingId(4L);
            }
            tblParkingVehicleInfoMapper.insert(tblParkingVehicleInfo);
        }
        if (out.contains(tblEventPlateInfo.getUbiSourceId())) {
            Example example = new Example(TblParkingVehicleInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("vehPlate", tblEventPlateInfo.getSzText());
            criteria.andEqualTo("exTime", null);
            TblParkingVehicleInfo tblParkingVehicleInfo = tblParkingVehicleInfoMapper.selectOneByExample(example);
            tblParkingVehicleInfo.setExTime(new Date(tblEventPlateInfo.getUbiTime()));
            tblParkingVehicleInfoMapper.insert(tblParkingVehicleInfo);
        }
    }
}
