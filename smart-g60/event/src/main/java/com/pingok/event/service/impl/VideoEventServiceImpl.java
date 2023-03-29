package com.pingok.event.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.TblEventRecord;
//import com.pingok.event.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.pingok.event.domain.videoEvent.*;
import com.pingok.event.mapper.TblEventRecordMapper;
import com.pingok.event.mapper.device.TblDeviceInfoMapper;
import com.pingok.event.mapper.videoEvent.*;
import com.pingok.event.service.IEventService;
import com.pingok.event.service.IVideoEventService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanUtils;
import com.ruoyi.common.core.utils.file.ImageUtils;
import com.ruoyi.system.api.RemoteDeviceMonitorService;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.SysFile;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author
 * @time 2022/5/6 21:28
 */
@Slf4j
@Service
public class VideoEventServiceImpl implements IVideoEventService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;

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
    private RemoteFileService remoteFileService;

    @Autowired
    private TblParkingLotMapper tblParkingLotMapper;

    @Autowired
    private TblParkingStatisticsMapper tblParkingStatisticsMapper;

    @Autowired
    private TblEventPassengerStatisticsMapper tblEventPassengerStatisticsMapper;

    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;

    @Autowired
    private TblEventRecordMapper tblEventRecordMapper;

    @Autowired
    private TblFaceInfoMapper tblFaceInfoMapper;

    @Autowired
    private TblDeviceInfoMapper tblDeviceInfoMapper;


    @Override
    public void faceInfo(TblFaceInfo tblFaceInfo) {

        tblFaceInfo.setId(remoteIdProducerService.nextId());
        tblFaceInfoMapper.insert(tblFaceInfo);

        Long fieldId = 0l;
        String workDate = DateUtils.dateTime(new Date(tblFaceInfo.getUbiTime()), DateUtils.YYYY_MM_DD);
        Integer hour = Integer.parseInt(DateUtils.dateTime(new Date(tblFaceInfo.getUbiTime()), DateUtils.HH));
        Integer areaId = 0;

        switch (tblFaceInfo.getSzSourceCode()) {
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
                areaId = 1;
                break;
            //南服务区艺海棠
            case "189":
                fieldId = 3940l;
                areaId = 5;
                break;
            //南服务区超市
            case "190":
            case "191":
                fieldId = 3940l;
                areaId = 2;
                break;
            //南服务区男厕
            case "192":
                fieldId = 3940l;
                areaId = 3;
                break;
            //南服务区女厕
            case "193":
            case "194":
                fieldId = 3940l;
                areaId = 4;
                break;
            //南服务区司机之家
            case "195":
                fieldId = 3940l;
                areaId = 6;
                break;
            default:
                return;
        }
        Example example = new Example(TblEventPassengerStatistics.class);
        example.createCriteria().andEqualTo("workDate", workDate)
                .andEqualTo("hour", hour)
                .andEqualTo("areaId", areaId)
                .andEqualTo("fieldId", fieldId);
        List<TblEventPassengerStatistics> list;
        TblEventPassengerStatistics statistics;
        Integer ageChild = 0;
        Integer ageYouth = 0;
        Integer ageMidlife = 0;
        Integer ageOld = 0;
        Integer sexMan = 0;
        Integer sexWoman = 0;
        Integer mask = 0;
        Integer noMask = 0;
        switch (tblFaceInfo.getUiAge()) {
            case 0:
                ageChild = 1;
                break;
            case 1:
                ageYouth = 1;
                break;
            case 2:
                ageMidlife = 1;
                break;
            case 3:
                ageOld = 1;
                break;
        }
        switch (tblFaceInfo.getUiSex()) {
            case 0:
                sexMan = 1;
                break;
            case 1:
                sexWoman = 1;
                break;
        }
        switch (tblFaceInfo.getUiMask()) {
            case 0:
                mask = 1;
                break;
            case 1:
                noMask = 1;
                break;
        }
        list = tblEventPassengerStatisticsMapper.selectByExample(example);
        if (list == null || list.size() <= 0) {
            statistics = new TblEventPassengerStatistics();
            statistics.setId(remoteIdProducerService.nextId());
            statistics.setWorkDate(workDate);
            statistics.setHour(hour);
            statistics.setAreaId(areaId);
            statistics.setFieldId(fieldId);
            statistics.setEntry(0);
            statistics.setOut(0);
            statistics.setInAmount(0);

            statistics.setAgeChild(ageChild);
            statistics.setAgeMidlife(ageMidlife);
            statistics.setAgeOld(ageOld);
            statistics.setAgeYouth(ageYouth);

            statistics.setSexMan(sexMan);
            statistics.setSexWoman(sexWoman);

            statistics.setMask(mask);
            statistics.setNoMask(noMask);

            tblEventPassengerStatisticsMapper.insert(statistics);

        } else {
            statistics = list.get(0);
            statistics.setAgeChild(ageChild + statistics.getAgeChild());
            statistics.setAgeMidlife(ageMidlife + statistics.getAgeMidlife());
            statistics.setAgeOld(ageOld + statistics.getAgeOld());
            statistics.setAgeYouth(ageYouth + statistics.getAgeYouth());

            statistics.setSexMan(sexMan + statistics.getSexMan());
            statistics.setSexWoman(sexWoman + statistics.getSexWoman());

            statistics.setMask(mask + statistics.getMask());
            statistics.setNoMask(noMask + statistics.getNoMask());
            tblEventPassengerStatisticsMapper.updateByPrimaryKey(statistics);
        }
    }

    @Override
    public void relieveEvent(TblEventVehicleEvent tblEventVehicleEvent) {
        Example example = new Example(TblEventVehicleEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventVehicleEvent.getUbiLogicId());
        TblEventVehicleEvent eventVehicleEvent = tblEventVehicleEventMapper.selectOneByExample(example);
        if (eventVehicleEvent == null) {
            eventVehicleEvent = new TblEventVehicleEvent();
            BeanUtils.copyNotNullProperties(tblEventVehicleEvent, eventVehicleEvent);
            eventVehicleEvent.setId(remoteIdProducerService.nextId());
            String imageUrl = null;
            if (StringUtils.isNotNull(tblEventVehicleEvent.getSzImg())) {
                MultipartFile multipartFile = ImageUtils.base64ToMultipartFile(tblEventVehicleEvent.getSzImg());
                R<SysFile> r = remoteFileService.upload(multipartFile);
                if (r.getCode() == R.SUCCESS) {
                    imageUrl = r.getData().getUrl();
                } else {
                    log.error("上传事件图片失败：" + r.getMsg());
                }
            }
            eventVehicleEvent.setSzImg(imageUrl);
            tblEventVehicleEventMapper.insert(eventVehicleEvent);
        }

        Integer uiEventType = null;
        switch (tblEventVehicleEvent.getUiEventType()) {
            case 31:
                uiEventType = 2;
                break;
            case 32:
                uiEventType = 7;
                break;
            case 34:
                uiEventType = 33;
                break;
            case 35:
                uiEventType = 23;
                break;
            case 36:
                uiEventType = 22;
                break;
            case 40:
                uiEventType = 38;
                break;
            case 41:
                uiEventType = 16;
                break;
        }

        if (StringUtils.isNotNull(tblEventVehicleEvent.getUiTrackId()) && StringUtils.isNotNull(uiEventType)) {
            example = new Example(TblEventVehicleEvent.class);
            example.createCriteria().andEqualTo("uiTrackId", tblEventVehicleEvent.getUiTrackId())
                    .andEqualTo("uiEventType", uiEventType);
            eventVehicleEvent = tblEventVehicleEventMapper.selectOneByExample(example);
            if (eventVehicleEvent != null) {
                TblEventRecord eventRecord = iEventService.selectByEventId(eventVehicleEvent.getUbiLogicId());
                if (eventRecord != null && eventRecord.getStatus() == 0) {
                    eventRecord.setStatus(2);
                    eventRecord.setUpdateTime(DateUtils.getNowDate());
                    iEventService.update(eventRecord);
                }
            }
        }

    }

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
        String imageUrl = null;
        if (StringUtils.isNotNull(tblEventVehicleEvent.getSzImg())) {
            MultipartFile multipartFile = ImageUtils.base64ToMultipartFile(tblEventVehicleEvent.getSzImg());
            R<SysFile> r = remoteFileService.upload(multipartFile);
            if (r.getCode() == R.SUCCESS) {
                imageUrl = r.getData().getUrl();
            } else {
                log.error("上传事件图片失败：" + r.getMsg());
            }
        }
        tblEventVehicleEvent.setSzImg(imageUrl);
        Example example = new Example(TblEventVehicleEvent.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("ubiLogicId", tblEventVehicleEvent.getUbiLogicId());
        TblEventVehicleEvent eventVehicleEvent = tblEventVehicleEventMapper.selectOneByExample(example);
        if (eventVehicleEvent == null) {
            eventVehicleEvent = new TblEventVehicleEvent();
            BeanUtils.copyNotNullProperties(tblEventVehicleEvent, eventVehicleEvent);
            eventVehicleEvent.setId(remoteIdProducerService.nextId());
            tblEventVehicleEventMapper.insert(eventVehicleEvent);

            String pileNo = null;
            Integer position = 0;
            R<TblDeviceInfo> r = remoteDeviceMonitorService.selectByDeviceId(eventVehicleEvent.getSzSourceCode());
            if (r.getCode() == R.SUCCESS && r.getData() != null) {
                pileNo = r.getData().getPileNo();
                position = Integer.parseInt(Pattern.compile("[^0-9]").matcher(pileNo).replaceAll("").trim());
            }

            if (eventVehicleEvent.getUiEventType() == 20) {
                Integer co = tblEventRecordMapper.checkBuildManage(DateUtils.dateTime(new Date(eventVehicleEvent.getUbiTime()), DateUtils.YYYY_MM_DD_HH_MM_SS), position);
                if (co != null && co > 0) {
                    return;
                }
            }

            TblEventRecord eventRecord = new TblEventRecord();
            eventRecord.setEventType(eventVehicleEvent.getUiEventType().toString());
            eventRecord.setVehClass(eventVehicleEvent.getUiVehicleTypeDetail());
            eventRecord.setVehColor(eventVehicleEvent.getUiVehiclePlateColor());
            eventRecord.setEventTime(new Date(eventVehicleEvent.getUbiTime()));
            eventRecord.setSpeed(eventVehicleEvent.getUiVehicleSpeed());
            eventRecord.setLane(eventVehicleEvent.getUiVehicleLane());
            eventRecord.setEventId(eventVehicleEvent.getUbiLogicId());
            eventRecord.setCreateTime(DateUtils.getNowDate());
            eventRecord.setSzSourceCode(eventVehicleEvent.getSzSourceCode());
            eventRecord.setStatus(0);
            eventRecord.setPileNo(pileNo);
            eventRecord.setIsFill(1);
            eventRecord.setDeviceType(eventVehicleEvent.getUiDeviceType());
            if (eventVehicleEvent.getUbiSourceId() != null) {
                Object o = tblEventVehicleEventMapper.findByDeviceId(eventVehicleEvent.getSzSourceCode());
                if (o != null) {
                    eventRecord.setLocationInterval(String.valueOf(o));
                }
            }
            eventRecord.setEventPhoto(imageUrl);
            iEventService.insert(eventRecord);
        }


    }

    @Override
    public void passengerFlow(TblEventPassengerFlow tblEventPassengerFlow) {
        tblEventPassengerFlow.setId(remoteIdProducerService.nextId());
        tblEventPassengerFlowMapper.insert(tblEventPassengerFlow);

        Long fieldId = 0l;
        String workDate = DateUtils.dateTime(new Date(tblEventPassengerFlow.getUbiEndTime()), DateUtils.YYYY_MM_DD);
        Integer hour = Integer.parseInt(DateUtils.dateTime(new Date(tblEventPassengerFlow.getUbiEndTime()), DateUtils.HH));
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
                areaId = 1;
                break;
            //南服务区艺海棠
            case "189":
                fieldId = 3940l;
                areaId = 5;
                break;
            //南服务区超市
            case "190":
            case "191":
                fieldId = 3940l;
                areaId = 2;
                break;
            //南服务区男厕
            case "192":
                fieldId = 3940l;
                areaId = 3;
                break;
            //南服务区女厕
            case "193":
            case "194":
                fieldId = 3940l;
                areaId = 4;
                break;
            //南服务区司机之家
            case "195":
                fieldId = 3940l;
                areaId = 6;
                break;
            default:
                return;
        }
        Example example = new Example(TblEventPassengerStatistics.class);
        example.createCriteria().andEqualTo("workDate", workDate)
                .andEqualTo("hour", hour)
                .andEqualTo("areaId", areaId)
                .andEqualTo("fieldId", fieldId);
        List<TblEventPassengerStatistics> list;
        TblEventPassengerStatistics statistics;
        list = tblEventPassengerStatisticsMapper.selectByExample(example);
        if (list == null || list.size() <= 0) {
            statistics = new TblEventPassengerStatistics();
            statistics.setId(remoteIdProducerService.nextId());
            statistics.setWorkDate(workDate);
            statistics.setHour(hour);
            statistics.setAreaId(areaId);
            statistics.setFieldId(fieldId);
            statistics.setEntry(entry);
            statistics.setOut(out);
            statistics.setAgeChild(0);
            statistics.setAgeMidlife(0);
            statistics.setAgeOld(0);
            statistics.setAgeYouth(0);
            statistics.setSexMan(0);
            statistics.setSexWoman(0);
            statistics.setMask(0);
            statistics.setNoMask(0);

//            example = new Example(TblEventPassengerStatistics.class);
//            example.createCriteria().andEqualTo("workDate", workDate)
//                    .andEqualTo("hour", Integer.parseInt(DateUtils.dateTime(DateUtils.getPreTime(DateUtils.getNowDate(), -60), DateUtils.HH)))
//                    .andEqualTo("areaId", areaId)
//                    .andEqualTo("fieldId", fieldId);
//            List<TblEventPassengerStatistics> infos = tblEventPassengerStatisticsMapper.selectByExample(example);
//            if (infos == null || infos.size() <= 0) {
//                statistics.setInAmount((entry - out) >= 0 ? (entry - out) : 0);
//            } else {
//                statistics.setInAmount((infos.get(0).getInAmount() + entry - out) >= 0 ? (infos.get(0).getInAmount() + entry - out) : 0);
//            }
            tblEventPassengerStatisticsMapper.insert(statistics);

        } else {
            statistics = list.get(0);
            statistics.setEntry(statistics.getEntry() + entry);
            statistics.setOut(statistics.getOut() + out);
            statistics.setInAmount((statistics.getInAmount() + entry - out) >= 0 ? (statistics.getInAmount() + entry - out) : 0);
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
        R<TblDeviceInfo> r;
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
                info.setParkingId(2L);
                info.setVehClassSub(tblEventPlateInfo.getUiSubType());
                info.setCreateTime(DateUtils.getNowDate());
                tblParkingVehicleInfoMapper.insert(info);

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3940)
                        .andEqualTo("regionNum", "H-B");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() - 1) > 0 ? (tblParkingLot.getSurplus() - 1) : 0);
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);

                r = remoteDeviceMonitorService.selectByDeviceId("FCMS03");
                if (r.getCode() == R.SUCCESS && r.getData() != null) {
                    JSONObject params = new JSONObject();
                    params.put("devIp",r.getData().getDeviceIp());
                    params.put("devPos",3);
                    params.put("infoType",1);
                    // 货B
                    params.put("text2BC_huoB",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <5){
                        //  0123-黑红绿黄
                        params.put("color2BC_huoB",1);
                    }else{
                        params.put("color2BC_huoB",2);
                    }
                    // 客A
                    example = new Example(TblParkingLot.class);
                    example.createCriteria().andEqualTo("fieldId", 3940)
                            .andEqualTo("regionNum", "K-A");
                    tblParkingLot = tblParkingLotMapper.selectOneByExample(example);

                    params.put("text2BC_ke",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <10){
                        //  0123-黑红绿黄
                        params.put("color2BC_ke",1);
                    }else{
                        params.put("color2BC_ke",2);
                    }
                    KafkaEnum kafkaEnum = new KafkaEnum();
                    kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PARKING);
                    kafkaEnum.setData(JSON.toJSONString(params));
                    remoteKafkaService.send(kafkaEnum);
                }
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
            case "202": //货A
//                查表中有入口时间无出口时间
                example = new Example(TblParkingVehicleInfo.class);
                example.createCriteria().andEqualTo("vehPlate", tblEventPlateInfo.getSzText())
                        .andIsNull("exTime");
                infoList = tblParkingVehicleInfoMapper.selectByExample(example);
                for (TblParkingVehicleInfo v : infoList) {
                    v.setParkingId(4L);
                    v.setUpdateTime(DateUtils.getNowDate());
                    tblParkingVehicleInfoMapper.updateByPrimaryKey(v);
                }

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3940)
                        .andEqualTo("regionNum", "H-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() - 1) > 0 ? (tblParkingLot.getSurplus() - 1) : 0);
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);

                r = remoteDeviceMonitorService.selectByDeviceId("FCMS02");
                if (r.getCode() == R.SUCCESS && r.getData() != null) {
                    JSONObject params = new JSONObject();
                    params.put("devIp",r.getData().getDeviceIp());
                    params.put("devPos",2);
                    params.put("infoType",1);
                    // 货A
                    params.put("text2A_huoA",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <5){
                        //  0123-黑红绿黄
                        params.put("color2A_huoA",1);
                    }else{
                        params.put("color2A_huoA",2);
                    }

                    // 货B
                    example = new Example(TblParkingLot.class);
                    example.createCriteria().andEqualTo("fieldId", 3940)
                            .andEqualTo("regionNum", "H-B");
                    tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                    params.put("text2A_huoB",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <5){
                        //  0123-黑红绿黄
                        params.put("color2A_huoB",1);
                    }else{
                        params.put("color2A_huoB",2);
                    }

                    // 客A
                    example = new Example(TblParkingLot.class);
                    example.createCriteria().andEqualTo("fieldId", 3940)
                            .andEqualTo("regionNum", "K-A");
                    tblParkingLot = tblParkingLotMapper.selectOneByExample(example);

                    params.put("text2A_ke",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <10){
                        //  0123-黑红绿黄
                        params.put("color2A_ke",1);
                    }else{
                        params.put("color2A_ke",2);
                    }
                    KafkaEnum kafkaEnum = new KafkaEnum();
                    kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PARKING);
                    kafkaEnum.setData(JSON.toJSONString(params));
                    remoteKafkaService.send(kafkaEnum);
                }

                break;
            case "203"://客A
                example = new Example(TblParkingVehicleInfo.class);
                example.createCriteria().andEqualTo("vehPlate", tblEventPlateInfo.getSzText())
                        .andIsNull("exTime");
                infoList = tblParkingVehicleInfoMapper.selectByExample(example);
                for (TblParkingVehicleInfo v : infoList) {
                    v.setParkingId(3L);
                    v.setUpdateTime(DateUtils.getNowDate());
                    tblParkingVehicleInfoMapper.updateByPrimaryKey(v);
                }

                example = new Example(TblParkingLot.class);
                example.createCriteria().andEqualTo("fieldId", 3940)
                        .andEqualTo("regionNum", "K-A");
                tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                tblParkingLot.setSurplus((tblParkingLot.getSurplus() - 1) > 0 ? (tblParkingLot.getSurplus() - 1) : 0);
                tblParkingLotMapper.updateByPrimaryKey(tblParkingLot);

                r = remoteDeviceMonitorService.selectByDeviceId("FCMS01");
                if (r.getCode() == R.SUCCESS && r.getData() != null) {
                    JSONObject params = new JSONObject();
                    params.put("devIp",r.getData().getDeviceIp());
                    params.put("devPos",1);
                    params.put("infoType",1);
                    // 客A
                    params.put("text1_ke",tblParkingLot.getSurplus());
                    if (tblParkingLot.getSurplus() <10){
                        //  0123-黑红绿黄
                        params.put("color1_ke",1);
                    }else{
                        params.put("color1_ke",2);
                    }

                    // 货A+货B
                    example = new Example(TblParkingLot.class);
                    example.createCriteria().andEqualTo("fieldId", 3940)
                            .andEqualTo("regionNum", "H-A");
                    tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                    int HA = tblParkingLot.getSurplus();

                    example = new Example(TblParkingLot.class);
                    example.createCriteria().andEqualTo("fieldId", 3940)
                            .andEqualTo("regionNum", "H-B");
                    tblParkingLot = tblParkingLotMapper.selectOneByExample(example);
                    int HB = tblParkingLot.getSurplus();

                    params.put("text1_huo",HA+HB);
                    if (tblParkingLot.getSurplus() <10){
                        //  0123-黑红绿黄
                        params.put("color1_huo",1);
                    }else{
                        params.put("color1_huo",2);
                    }

                    KafkaEnum kafkaEnum = new KafkaEnum();
                    kafkaEnum.setTopIc(KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PARKING);
                    kafkaEnum.setData(JSON.toJSONString(params));
                    remoteKafkaService.send(kafkaEnum);
                }
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
        if (vehType != 0 && fieldId != 0) {
            List<TblParkingStatistics> list;
            TblParkingStatistics statistics;
            Example example = new Example(TblParkingStatistics.class);
            example.createCriteria().andEqualTo("fieldId", fieldId)
                    .andEqualTo("vehType", vehType)
                    .andEqualTo("day", day)
                    .andEqualTo("hour", hour);
            list = tblParkingStatisticsMapper.selectByExample(example);
            if (list == null || list.size() <= 0) {
                statistics = new TblParkingStatistics();
                statistics.setId(remoteIdProducerService.nextId());
                statistics.setDay(day);
                statistics.setCurrentNum(currentNum >= 0 ? currentNum : 0);
                statistics.setEnter(enter);
                statistics.setHour(hour);
                statistics.setFieldId(fieldId);
                statistics.setOut(out);
                statistics.setVehType(vehType);
                tblParkingStatisticsMapper.insert(statistics);

            } else {
                statistics = list.get(0);
                statistics.setEnter(statistics.getEnter() + enter);
                statistics.setOut(statistics.getOut() + out);
                statistics.setCurrentNum((statistics.getCurrentNum() + currentNum) >= 0 ? (statistics.getCurrentNum() + currentNum) : 0);
                tblParkingStatisticsMapper.updateByPrimaryKey(statistics);
            }
        }
    }
}
