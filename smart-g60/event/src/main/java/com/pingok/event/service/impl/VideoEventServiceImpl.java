package com.pingok.event.service.impl;

import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.domain.videoEvent.*;
import com.pingok.event.mapper.videoEvent.*;
import com.pingok.event.service.IEventService;
import com.pingok.event.service.IVideoEventService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

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

    @Autowired
    private TblParkingLotMapper tblParkingLotMapper;

    @Autowired
    private TblParkingStatisticsMapper tblParkingStatisticsMapper;

    @Autowired
    private TblEventPassengerStatisticsMapper tblEventPassengerStatisticsMapper;


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
        tblEventPlateInfo.setId(remoteIdProducerService.nextId());
        tblEventPlateInfoMapper.insert(tblEventPlateInfo);
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
//            eventRecord.setEventPhoto(eventVehicleEvent.getSzImg());
            eventRecord.setEventTime(new Date(eventVehicleEvent.getUbiTime()));
            eventRecord.setSpeed(eventVehicleEvent.getUiVehicleSpeed());
            eventRecord.setLane(eventVehicleEvent.getUiVehicleLane());
            eventRecord.setEventId(eventVehicleEvent.getUbiLogicId());
            eventRecord.setCreateTime(DateUtils.getNowDate());
            eventRecord.setSzSourceCode(eventVehicleEvent.getSzSourceCode());
            eventRecord.setStatus(0);
            if (eventVehicleEvent.getUbiSourceId() != null) {
                Object o = tblEventVehicleEventMapper.findByDeviceId(eventVehicleEvent.getSzSourceCode());
                if (o != null) {
                    eventRecord.setLocationInterval(String.valueOf(o));
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

        Long fieldId = 0l;
        String workDate = DateUtils.getDate();
        Integer hour = Integer.parseInt(DateUtils.dateTime(DateUtils.getNowDate(), DateUtils.HH));
        Integer areaId = 0;
        Integer entry = tblEventPassengerFlow.getUiGetInPeos();
        Integer out = tblEventPassengerFlow.getUiGetOutPeos();
        switch (tblEventPassengerFlow.getSzSourceCode()) {
            //北服务区商铺
            case "181":
            case "182":
                fieldId = 3941l;
                areaId = 1;
                break;
            //北服务区小超市
            case "183":
                fieldId = 3941l;
                areaId = 2;
                break;
            //北服务区男厕
            case "184":
                fieldId = 3941l;
                areaId = 3;
                break;
            //北服务区女厕
            case "185":
                fieldId = 3941l;
                areaId = 4;
                break;
            //南服务区商铺
            case "186":
            case "187":
            case "188":
                fieldId = 3940l;
                areaId = 5;
                break;
            //南服务区艺海棠
            case "189":
                fieldId = 3940l;
                areaId = 6;
                break;
            //南服务区超市
            case "190":
            case "191":
                fieldId = 3940l;
                areaId = 7;
                break;
            //南服务区男厕
            case "192":
                fieldId = 3940l;
                areaId = 8;
                break;
            //南服务区女厕
            case "193":
            case "194":
                fieldId = 3940l;
                areaId = 9;
                break;
            //南服务区司机之家
            case "195":
                fieldId = 3940l;
                areaId = 10;
                break;
            default:
                return;
        }
        Example example = new Example(TblEventPassengerStatistics.class);
        example.createCriteria().andEqualTo("workDate", workDate)
                .andEqualTo("hour", hour)
                .andEqualTo("areaId", areaId)
                .andEqualTo("fieldId", fieldId);
        TblEventPassengerStatistics statistics = tblEventPassengerStatisticsMapper.selectOneByExample(example);
        if (statistics == null) {
            statistics = new TblEventPassengerStatistics();
            statistics.setId(remoteIdProducerService.nextId());
            statistics.setWorkDate(workDate);
            statistics.setHour(hour);
            statistics.setAreaId(areaId);
            statistics.setFieldId(fieldId);
            statistics.setEntry(entry);
            statistics.setOut(out);


            TblEventPassengerStatistics info = tblEventPassengerStatisticsMapper.selectOneByExample(example);
            if (info == null) {
                example = new Example(TblEventPassengerStatistics.class);
                example.createCriteria().andEqualTo("workDate", workDate)
                        .andEqualTo("hour", Integer.parseInt(DateUtils.dateTime(DateUtils.getPreTime(DateUtils.getNowDate(), -60), DateUtils.HH)))
                        .andEqualTo("areaId", areaId)
                        .andEqualTo("fieldId", fieldId);
                info = tblEventPassengerStatisticsMapper.selectOneByExample(example);
                if (info == null) {
                    statistics.setInAmount(entry - out);
                } else {
                    statistics.setInAmount(info.getInAmount() + entry - out);
                }
                tblEventPassengerStatisticsMapper.insert(statistics);
            } else {
                info.setEntry(info.getEntry() + entry);
                info.setOut(info.getOut() + out);
                info.setInAmount(info.getInAmount() + entry - out);
                tblEventPassengerStatisticsMapper.updateByPrimaryKey(info);
            }
        } else {
            statistics.setEntry(statistics.getEntry() + entry);
            statistics.setOut(statistics.getOut() + out);
            statistics.setInAmount(statistics.getInAmount() + entry - out);
            tblEventPassengerStatisticsMapper.updateByPrimaryKey(statistics);
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

    @Override
    public void parkVehInfo(TblEventPlateInfo tblEventPlateInfo) {
        TblParkingVehicleInfo info;
        List<TblParkingVehicleInfo> infoList;
        Example example;
        String dateTime = tblEventPlateInfo.getUiYear() + "-" + tblEventPlateInfo.getUiMonth() + "-" + tblEventPlateInfo.getUiDay() + " " + tblEventPlateInfo.getUiHour() + ":" + tblEventPlateInfo.getUiMin() + ":" + "00";
        TblParkingLot tblParkingLot;
        switch (tblEventPlateInfo.getSzSourceCode()) {
            //北区入总
            case "197":
            case "198":
                info = new TblParkingVehicleInfo();
                info.setId(remoteIdProducerService.nextId());
                info.setEnTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, dateTime));
                info.setVehPlate(tblEventPlateInfo.getSzText());
                info.setVehClass(tblEventPlateInfo.getUiStatType());
                info.setVehColor(tblEventPlateInfo.getUiColor());
                info.setParkingId(1L);
                info.setVehClassSub(tblEventPlateInfo.getUiSubType());
                info.setCreateTime(DateUtils.getNowDate());
                tblParkingVehicleInfoMapper.insert(info);

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3941)
                        .andEqualTo("regionNum", "K-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() - 1) > 0 ? (tblParkingLot.getSurplus() - 1) : 0);
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
                break;
            //南区入总
            case "200":
                info = new TblParkingVehicleInfo();
                info.setId(remoteIdProducerService.nextId());
                info.setEnTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, dateTime));
                info.setVehPlate(tblEventPlateInfo.getSzText());
                info.setVehClass(tblEventPlateInfo.getUiStatType());
                info.setVehColor(tblEventPlateInfo.getUiColor());
                info.setParkingId(3L);
                info.setVehClassSub(tblEventPlateInfo.getUiSubType());
                info.setCreateTime(DateUtils.getNowDate());
                tblParkingVehicleInfoMapper.insert(info);

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3940)
                        .andEqualTo("regionNum", "K-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() - 1) > 0 ? (tblParkingLot.getSurplus() - 1) : 0);
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
                break;
            //南出
            case "201":
                example = new Example(TblParkingVehicleInfo.class);
                example.createCriteria().andEqualTo("vehPlate", tblEventPlateInfo.getSzText())
                        .andIsNull("exTime");
                infoList = tblParkingVehicleInfoMapper.selectByExample(example);
                for (TblParkingVehicleInfo v : infoList) {
                    v.setExTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, dateTime));
                    v.setUpdateTime(DateUtils.getNowDate());
                    tblParkingVehicleInfoMapper.updateByPrimaryKey(v);
                }

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3940)
                        .andEqualTo("regionNum", "K-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() + 1) <= tblParkingLot.getTotal() ? (tblParkingLot.getSurplus() + 1) : tblParkingLot.getTotal());
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
                break;
            //北出
            case "199":
                example = new Example(TblParkingVehicleInfo.class);
                example.createCriteria().andEqualTo("vehPlate", tblEventPlateInfo.getSzText())
                        .andIsNull("exTime");
                infoList = tblParkingVehicleInfoMapper.selectByExample(example);
                for (TblParkingVehicleInfo v : infoList) {
                    v.setExTime(DateUtils.dateTime(DateUtils.YYYY_MM_DD_HH_MM_SS, dateTime));
                    v.setUpdateTime(DateUtils.getNowDate());
                    tblParkingVehicleInfoMapper.updateByPrimaryKey(v);
                }
                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3941)
                        .andEqualTo("regionNum", "K-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() + 1) <= tblParkingLot.getTotal() ? (tblParkingLot.getSurplus() + 1) : tblParkingLot.getTotal());
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);
                break;
        }
    }

    @Override
    public void parkingStatistics(TblEventPlateInfo tblEventPlateInfo) {
        Long fieldId = 0l;
        Integer enter = 0;
        Integer out = 0;
        Integer vehType = 0;
        Integer currentNum = 0;
        Integer hour = tblEventPlateInfo.getUiHour();
        Date day = DateUtils.dateTime(DateUtils.YYYY_MM_DD, (tblEventPlateInfo.getUiYear() + "-" + tblEventPlateInfo.getUiMonth() + "-" + tblEventPlateInfo.getUiDay()));
        switch (tblEventPlateInfo.getSzSourceCode()) {
            //北区入总
            case "197":
            case "198":
                fieldId = 3941l;
                enter = 1;
                currentNum = 1;
                break;
            //南区入总
            case "200":
                fieldId = 3940l;
                enter = 1;
                currentNum = 1;
                break;
            //南出
            case "201":
                fieldId = 3940l;
                out = 1;
                currentNum = -1;
                break;
            //北出
            case "199":
                fieldId = 3941l;
                out = 1;
                currentNum = -1;
                break;
        }
        switch (tblEventPlateInfo.getUiSubType()) {
            //客车
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 25:
            case 29:
            case 30:
            case 37:
            case 38:
                vehType = 1;
                break;
            //货车
            case 0:
            case 1:
            case 2:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 31:
            case 33:
            case 34:
            case 39:
            case 40:
                vehType = 2;
                break;
        }
        TblParkingStatistics statistics;
        Example example = new Example(TblParkingStatistics.class);
        example.createCriteria().andEqualTo("fieldId", fieldId)
                .andEqualTo("vehType", vehType)
                .andEqualTo("day", day)
                .andEqualTo("hour", hour);
        statistics = tblParkingStatisticsMapper.selectOneByExample(example);
        if (StringUtils.isNull(statistics)) {
            statistics = new TblParkingStatistics();
            statistics.setId(remoteIdProducerService.nextId());
            statistics.setDay(day);
            statistics.setCurrentNum(currentNum);
            statistics.setEnter(enter);
            statistics.setHour(hour);
            statistics.setFieldId(fieldId);
            statistics.setOut(out);
            statistics.setVehType(vehType);

            TblParkingStatistics info = tblParkingStatisticsMapper.selectOneByExample(example);
            if (info == null) {
                tblParkingStatisticsMapper.insert(statistics);
            } else {
                info.setEnter(info.getEnter() + enter);
                info.setOut(info.getOut() + out);
                info.setCurrentNum(info.getCurrentNum() + currentNum);
                tblParkingStatisticsMapper.updateByPrimaryKey(info);
            }
        } else {
            statistics.setEnter(statistics.getEnter() + enter);
            statistics.setOut(statistics.getOut() + out);
            statistics.setCurrentNum(statistics.getCurrentNum() + currentNum);
            tblParkingStatisticsMapper.updateByPrimaryKey(statistics);
        }
    }
}
