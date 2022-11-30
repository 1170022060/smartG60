package com.pingok.event.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.TblEventAlarm;
import com.pingok.event.domain.TblEventHandle;
import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.mapper.TblEventAlarmMapper;
import com.pingok.event.mapper.TblEventHandleMapper;
import com.pingok.event.mapper.TblEventRecordMapper;
import com.pingok.event.service.IEventService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.DictUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.*;
import com.ruoyi.system.api.domain.SysDictData;
import com.ruoyi.system.api.domain.amap.TblAutoNaviMapRecord;
import com.ruoyi.system.api.domain.device.TblDeviceInfo;
import com.ruoyi.system.api.domain.emergency.TblEmergencySupplies;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import com.ruoyi.system.api.domain.release.TblReleasePreset;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * 事件服务 服务层处理
 *
 * @author ruoyi
 */
@Service
public class EventServiceImpl implements IEventService {

    @Autowired
    private TblEventRecordMapper tblEventRecordMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblEventHandleMapper tblEventHandleMapper;
    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteInfoBoardService remoteInfoBoardService;
    @Autowired
    private RemoteReleaseService remoteReleaseService;
    @Autowired
    private RemoteDeviceInfoService remoteDeviceInfoService;
    @Autowired
    private RemoteAmapService remoteAmapService;
    @Autowired
    private RemoteBaiDuService remoteBaiDuService;
    @Autowired
    private RemotePilotLightService remotePilotLightService;
    @Autowired
    private RemoteEmergencySuppliesService remoteEmergencySuppliesService;

    @Autowired
    private TblEventAlarmMapper tblEventAlarmMapper;

    @Autowired
    private RemoteDeviceMonitorService remoteDeviceMonitorService;


    @Override
    public TblEventRecord selectByEventTypeAndPileNo(String eventType, String pileNo) {
        List<Integer> status = Arrays.asList(0,1);
        Example example = new Example(TblEventRecord.class);
        example.orderBy("eventTime").desc();
        example.createCriteria().andEqualTo("eventType", eventType)
                .andEqualTo("pileNo",pileNo)
                .andIn("status", status);
        return tblEventRecordMapper.selectByExample(example).get(0);
    }

    @Override
    public TblEventRecord selectByEventId(Long eventId) {
        Example example = new Example(TblEventRecord.class);
        example.createCriteria().andEqualTo("eventId", eventId);
        return tblEventRecordMapper.selectOneByExample(example);
    }

    @Override
    public List<String> eventAlarmAll() {
        List<TblEventAlarm> list = tblEventAlarmMapper.selectAll();
        List<String> types = new ArrayList<>();
        for (TblEventAlarm i : list) {
            types.add(i.getEventType().toString());
        }
        return types;
    }

    @Override
    public void eventAlarmAdd(List<Integer> eventTypes) {
        List<TblEventAlarm> list = tblEventAlarmMapper.selectAll();
        for (TblEventAlarm t : list) {
            tblEventAlarmMapper.delete(t);
        }
        TblEventAlarm eventAlarm;
        if (eventTypes != null && eventTypes.size() > 0) {
            for (Integer i : eventTypes) {
                eventAlarm = new TblEventAlarm();
                eventAlarm.setId(remoteIdProducerService.nextId());
                eventAlarm.setEventType(i);
                tblEventAlarmMapper.insert(eventAlarm);
            }
        }
    }

    @Override
    public List<Map> searchEvent() {
        return tblEventRecordMapper.searchEvent();
    }

    @Override
    public void updateEventVideo(Long ubiLogicId, String url) {
        Example example = new Example(TblEventRecord.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("eventId", ubiLogicId);
        TblEventRecord eventRecord = tblEventRecordMapper.selectOneByExample(example);
        if (eventRecord == null) {
            throw new ServiceException("保存事件视频失败，事件id不存在");
        }
        eventRecord.setVideo(url);
        tblEventRecordMapper.updateByPrimaryKey(eventRecord);
    }

    @Override
    public TblEventRecord report(Long id) {
        TblEventRecord eventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        List<SysDictData> sysDictDataList = DictUtils.getDictCache("event_type");
        for (SysDictData d : sysDictDataList) {
            if (eventRecord.getEventType().equals(d.getDictValue())) {
                eventRecord.setEventTypeLabel(d.getDictLabel());
            }
        }
        if (eventRecord != null) {
            eventRecord.setEventHandles(tblEventHandleMapper.findByEventId(eventRecord.getId()));
        }
        return eventRecord;
    }

    @Override
    public void relieve(Long id) {
        TblEventRecord tblEventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        tblEventRecord.setStatus(2);
        tblEventRecord.setRelieveTime(DateUtils.getNowDate());
        tblEventRecord.setRelieveUserId(SecurityUtils.getUserId());
        tblEventRecordMapper.updateByPrimaryKey(tblEventRecord);

        TblEventHandle tblEventHandle = new TblEventHandle();
        tblEventHandle.setId(remoteIdProducerService.nextId());
        tblEventHandle.setEventId(id);
        tblEventHandle.setHandleTime(DateUtils.getNowDate());
        tblEventHandle.setCreateTime(DateUtils.getNowDate());
        tblEventHandle.setUserId(SecurityUtils.getUserId());
        tblEventHandle.setHandleContent("事件解除");
        tblEventHandleMapper.insert(tblEventHandle);
//
//        JSONObject event = new JSONObject();
//        event.put("id", tblEventRecord.getId());
//        event.put("time", tblEventRecord.getEventTime());
//
//        JSONObject data = new JSONObject();
//        data.put("type", "eventRelease");
//        data.put("data", event.toJSONString());
//        KafkaEnum kafkaEnum = new KafkaEnum();
//        kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
//        kafkaEnum.setData(data.toJSONString());
//        remoteKafkaService.send(kafkaEnum);
    }

    @Override
    public void handleContent(List<TblEventHandle> tblEventHandles) {
        for (TblEventHandle tblEventHandle : tblEventHandles) {
            tblEventHandle.setId(remoteIdProducerService.nextId());
            tblEventHandle.setCreateTime(DateUtils.getNowDate());
            tblEventHandle.setUserId(SecurityUtils.getUserId());
            tblEventHandle.setHandleTime(DateUtils.getNowDate());
            tblEventHandleMapper.insert(tblEventHandle);
        }
    }

    @Override
    public void handle(Long id, JSONArray eventPlan) {
        TblEventRecord tblEventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        if (tblEventRecord == null) {
            throw new SecurityException("id为：" + id + ",事件信息不存在");
        }
        JSONObject plan;
        TblEventHandle tblEventHandle;
        tblEventHandle = new TblEventHandle();
        tblEventHandle.setId(remoteIdProducerService.nextId());
        tblEventHandle.setEventId(id);
        tblEventHandle.setHandleTime(DateUtils.getNowDate());
        tblEventHandle.setCreateTime(DateUtils.getNowDate());
        tblEventHandle.setUserId(SecurityUtils.getUserId());
        tblEventHandle.setHandleContent("开始应急处置");
        tblEventHandleMapper.insert(tblEventHandle);
        JSONObject vmsPublishInfo;
        R<TblDeviceInfo> rDevice;
        R<TblReleasePreset> rReleasePreset;
        TblReleasePreset tblReleasePreset;
        R r;
        TblDeviceInfo deviceInfo;
        JSONArray devicdIds;
        JSONArray infos;
        JSONObject info;
        JSONObject data;
        String content;
        TblAutoNaviMapRecord autoNaviMapRecord;
        int size;
        for (int i = 0; i < eventPlan.size(); i++) {
            tblEventHandle = new TblEventHandle();
            tblEventHandle.setId(remoteIdProducerService.nextId());
            tblEventHandle.setEventId(id);
            tblEventHandle.setHandleTime(DateUtils.getNowDate());
            tblEventHandle.setCreateTime(DateUtils.getNowDate());
            tblEventHandle.setUserId(SecurityUtils.getUserId());
            content = null;
            plan = eventPlan.getJSONObject(i);
            tblEventHandle.setType(plan.getInteger("type"));
            switch (plan.getInteger("type")) {
                case 4://可变信息标志推送
                    content = "情报板信息发布：";
                    rReleasePreset = remoteReleaseService.idInfo(plan.getLong("presetId"));
                    if (rReleasePreset != null) {
                        if (rReleasePreset.getCode() == R.SUCCESS) {
                            if (plan.containsKey("deviceIds")) {
                                vmsPublishInfo = new JSONObject();
                                devicdIds = plan.getJSONArray("deviceIds");
                                if (devicdIds != null && devicdIds.size() > 0) {
                                    size = devicdIds.size();
                                    infos = new JSONArray();
                                    for (int j = 0; j < size; j++) {
                                        rDevice = remoteDeviceMonitorService.selectByDeviceId(devicdIds.getString(j));
                                        deviceInfo = rDevice.getData();
                                        info = new JSONObject();
                                        info.put("devId", deviceInfo.getDeviceId());
                                        info.put("ip", deviceInfo.getDeviceIp());
                                        info.put("port", deviceInfo.getPort());
                                        info.put("slave", deviceInfo.getSlaveId());
                                        info.put("protocol", deviceInfo.getProtocol());
                                        info.put("width", deviceInfo.getWidth());
                                        info.put("height", deviceInfo.getHigh());
                                        infos.add(info);
                                    }
                                    vmsPublishInfo.put("devInfo", infos);

                                    tblReleasePreset = rReleasePreset.getData();
                                    info = new JSONObject();
                                    info.put("content", tblReleasePreset.getPresetInfo());
                                    info.put("typeface", tblReleasePreset.getTypeface());
                                    info.put("textColor", tblReleasePreset.getColor());
                                    info.put("textSize", tblReleasePreset.getTypefaceSize());
                                    info.put("picId", tblReleasePreset.getPictureType());
                                    infos = new JSONArray();
                                    infos.add(info);
                                    vmsPublishInfo.put("data", infos);
                                    r = remoteInfoBoardService.publish(vmsPublishInfo);
                                    if (r.getCode() == R.SUCCESS) {
                                        content += "发布成功";
                                    }
                                }else {
                                    content += "发布失败，无设备信息";
                                }
                            }else {
                                content += "发布失败，无设备信息";
                            }

                        } else {
                            content += "ID为：" + plan.getLong("presetId") + "：预设信息查询失败，失败原因" + rReleasePreset.getMsg() + "；";
                        }
                    } else {
                        content += "ID为：" + plan.getLong("presetId") + "的情报板预设信息不存在，推送失败；";
                    }
                    break;
                case 5://可变限速标志推送
                    content = "限速板信息发布：";
                    if (plan.containsKey("deviceIds")) {
                        vmsPublishInfo = new JSONObject();
                        devicdIds = plan.getJSONArray("deviceIds");
                        if (devicdIds != null && devicdIds.size() > 0) {
                            size = devicdIds.size();
                            infos = new JSONArray();
                            for (int j = 0; j < size; j++) {
                                rDevice = remoteDeviceMonitorService.selectByDeviceId(devicdIds.getString(j));
                                deviceInfo = rDevice.getData();
                                info = new JSONObject();
                                info.put("devId", deviceInfo.getDeviceId());
                                info.put("ip", deviceInfo.getDeviceIp());
                                info.put("port", deviceInfo.getPort());
                                info.put("slave", deviceInfo.getSlaveId());
                                info.put("protocol", deviceInfo.getProtocol());
                                info.put("width", deviceInfo.getWidth());
                                info.put("height", deviceInfo.getHigh());
                                infos.add(info);
                            }
                            if (plan.containsKey("presetId")) {
                                vmsPublishInfo.put("devInfo", infos);
                                info = new JSONObject();
                                info.put("picId", plan.getLong("presetId"));
                                infos = new JSONArray();
                                infos.add(info);
                                vmsPublishInfo.put("data", infos);
                                r = remoteInfoBoardService.publish(vmsPublishInfo);
                                if (r.getCode() == R.SUCCESS) {
                                    content += "发布成功";
                                }
                            } else {
                                content += "发布失败，发布内容为空";
                            }
                        }else {
                            content += "发布失败，无设备信息";
                        }
                    }else {
                        content += "发布失败，无设备信息";
                    }
                    break;
                case 6://ETC门架车路协同推送
                    break;
                case 7://超视距诱导灯推送
                    content = "超视距诱导模式推送：";
                    if (plan.containsKey("deviceIds")) {
                        JSONObject body = new JSONObject();
                        devicdIds = plan.getJSONArray("deviceIds");
                        if (devicdIds != null && devicdIds.size() > 0) {
                            size = devicdIds.size();
                            if (plan.containsKey("cmdType")) {
                                for (int j = 0; j < size; j++) {
                                    rDevice = remoteDeviceMonitorService.selectByDeviceId(devicdIds.getString(j));
                                    if (rDevice != null && rDevice.getCode() == R.SUCCESS) {
                                        deviceInfo = rDevice.getData();
                                        body.put("deviceId", deviceInfo.getDeviceId());
                                        body.put("cmdType", plan.getJSONArray("cmdType"));
                                        r = remotePilotLightService.send(body);
                                        if (r != null) {
                                            if (r != null && r.getCode() == R.SUCCESS) {
                                                content += deviceInfo.getDeviceName() + "：推送成功；";
                                            } else {
                                                content += deviceInfo.getDeviceName() + "：推送失败，失败原因" + r.getMsg() + "；";
                                            }
                                        } else {
                                            content += deviceInfo.getDeviceName() + "：推送失败，原因：服务无响应；";
                                        }
                                    } else {
                                        content += "ID为：" + devicdIds.getLong(j) + "的超视距诱导设备信息不存在，推送失败；";
                                    }
                                }
                            }else {
                                content += "发布失败，模式信息为空";
                            }
                        }else {
                            content += "发布失败，无设备信息";
                        }
                    }else {
                        content += "发布失败，无设备信息";
                    }

                    break;
                case 8://高德地图推送
                    content = "高德地图事件推送：";
                    autoNaviMapRecord = new TblAutoNaviMapRecord();
                    autoNaviMapRecord.setId(id);
                    autoNaviMapRecord.setEventType(plan.getInteger("amapType"));
                    autoNaviMapRecord.setLocs(JSON.toJSONString(Arrays.asList(Arrays.asList(tblEventRecord.getPileNo()))));
                    autoNaviMapRecord.setStartDate(tblEventRecord.getEventTime());
                    autoNaviMapRecord.setEventDesc(tblEventRecord.getRemark());
                    autoNaviMapRecord.setDirection(tblEventRecord.getDirection());
                    r = remoteAmapService.eventPublish(autoNaviMapRecord);
                    if (r != null) {
                        if (r.getCode() == R.SUCCESS) {
                            content += "推送成功";
                        } else {
                            content += "推送失败，原因：" + r.getMsg();
                        }
                    } else {
                        content += "推送失败，原因：服务无响应；";
                    }
                    break;
                case 10://百度地图推送
//                    content = "百度地图事件推送：";
//                    TblBaiDuMapRecord baiDuMapRecord = new TblBaiDuMapRecord();
//                    baiDuMapRecord.setEventType(plan.getInteger("amapType"));
//                    baiDuMapRecord.setLevel(1);
//                    baiDuMapRecord.setTraffic(2);
//                    baiDuMapRecord.setDirection(tblEventRecord.getDirection());
//                    baiDuMapRecord.setStartTime(DateUtils.getDateShortTimestamp(tblEventRecord.getEventTime()).intValue());
//                    baiDuMapRecord.setEndTime(DateUtils.getDateShortTimestamp(DateUtils.getPreTime(tblEventRecord.getEventTime(), 600)).intValue());
//                    baiDuMapRecord.setContent(tblEventRecord.getRemark());
//                    baiDuMapRecord.setLocation(tblEventRecord.getLocationInterval());
//                    baiDuMapRecord.setLocationType(1);
//                    baiDuMapRecord.setDataType("test");
//                    r = remoteBaiDuService.eventPublish(baiDuMapRecord);
//                    if (r != null) {
//                        if (r.getCode() == R.SUCCESS) {
//                            content += "推送成功";
//                        } else {
//                            content += "推送失败，原因：" + r.getMsg();
//                        }
//                    } else {
//                        content += "推送失败，原因：服务无响应；";
//                    }
                    break;
                case 9://应急资源
                    content = "应急资源：";
                    JSONArray materials = plan.getJSONArray("materials");
                    JSONObject object;
                    if (materials != null && materials.size() > 0) {
                        size = materials.size();
                        TblEmergencySupplies emergencySupplies;
                        R<TblEmergencySupplies> emergencySuppliesR;
                        for (int j = 0; j < size; j++) {
                            object = materials.getJSONObject(j);
                            emergencySuppliesR = remoteEmergencySuppliesService.idInfo(object.getLong("materialId"));
                            if (emergencySuppliesR == null) {
                                if (emergencySuppliesR.getCode() == R.SUCCESS) {
                                    emergencySupplies = emergencySuppliesR.getData();
                                    switch (emergencySupplies.getSuppliesType()) {
                                        case 1: //物资
                                            content += "应急物资：" + emergencySupplies.getSuppliesName() + "，数量：" + emergencySupplies.getSuppliesCount() + "；";
                                            break;
                                        case 2: //专家
                                            content += "应急专家：" + emergencySupplies.getExpertName() + "，联系电话：" + emergencySupplies.getExpertPhone() + "；";
                                            break;
                                        case 3: //车辆
                                            content += "应急车辆：" + emergencySupplies.getVehPlate() + "，车牌颜色：" + emergencySupplies.getVehColor() + "，车型：" + emergencySupplies.getVehClass() + "；";
                                            break;
                                    }
                                } else {
                                    content += "ID为：" + object.getLong("materialId") + "的应急资源信息查询失败，原因：" + emergencySuppliesR.getMsg() + "；";
                                }
                            } else {
                                content += "ID为：" + object.getLong("materialId") + "的应急资源信息查询失败，原因：应急资源查询服务无响应；";
                            }

                        }
                    }
                    break;
            }
            tblEventHandle.setHandleContent(content);
            tblEventHandleMapper.insert(tblEventHandle);
        }
    }

    @Override
    public void fault(Long id, String remark) {
        TblEventRecord tblEventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        tblEventRecord.setStatus(-1);
        tblEventRecord.setUpdateTime(DateUtils.getNowDate());
        tblEventRecord.setUpdateUserId(SecurityUtils.getUserId());
        tblEventRecord.setRemark(remark);
        tblEventRecordMapper.updateByPrimaryKey(tblEventRecord);

//        eventUpdate(tblEventRecord.getEventId(), 1, null, SecurityUtils.getUsername());
    }

    @Override
    public void confirm(Long id, String eventType, String remark, String direction) {
        TblEventRecord tblEventRecord = tblEventRecordMapper.selectByPrimaryKey(id);
        tblEventRecord.setStatus(1);
        tblEventRecord.setConfirmTime(DateUtils.getNowDate());
        tblEventRecord.setConfirmUserId(SecurityUtils.getUserId());
        tblEventRecord.setEventType(eventType);
        tblEventRecord.setRemark(remark);
        tblEventRecord.setDirection(direction);
        tblEventRecordMapper.updateByPrimaryKey(tblEventRecord);

        TblEventHandle tblEventHandle = new TblEventHandle();
        tblEventHandle.setId(remoteIdProducerService.nextId());
        tblEventHandle.setEventId(tblEventRecord.getId());
        tblEventHandle.setHandleTime(tblEventRecord.getEventTime());
        tblEventHandle.setCreateTime(DateUtils.getNowDate());
        tblEventHandle.setUserId(SecurityUtils.getUserId());
        tblEventHandle.setHandleContent("事件确认");
        tblEventHandleMapper.insert(tblEventHandle);

        JSONObject data = new JSONObject();
        data.put("ubiLogicId",tblEventRecord.getEventId());
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
        kafkaEnum.setData(data.toJSONString());
        remoteKafkaService.send(kafkaEnum);



//        eventUpdate(tblEventRecord.getEventId(), 2, null, SecurityUtils.getUsername());
    }

    @Override
    public List<Map> search(Integer status) {
        return tblEventRecordMapper.search(status);
    }

    @Override
    public TblEventRecord findById(Long id) {
        return tblEventRecordMapper.selectByPrimaryKey(id);
    }

    @Override
    public int insert(TblEventRecord tblEventRecord) {
        tblEventRecord.setId(remoteIdProducerService.nextId());
        tblEventRecord.setCreateTime(DateUtils.getNowDate());
        tblEventRecord.setCreateUserId(SecurityUtils.getUserId());
        tblEventRecord.setStatus(0);
        int r = tblEventRecordMapper.insert(tblEventRecord);

        TblEventHandle tblEventHandle = new TblEventHandle();
        tblEventHandle.setId(remoteIdProducerService.nextId());
        tblEventHandle.setEventId(tblEventRecord.getId());
        tblEventHandle.setHandleTime(tblEventRecord.getEventTime());
        tblEventHandle.setCreateTime(DateUtils.getNowDate());
        tblEventHandle.setUserId(SecurityUtils.getUserId());
        tblEventHandle.setHandleContent("事件发生");
        tblEventHandleMapper.insert(tblEventHandle);

        List<TblEventAlarm> list = tblEventAlarmMapper.selectAll();

        if (list == null || list.size() <= 0) {
            JSONObject event = new JSONObject();
            event.put("id", tblEventRecord.getId());
            event.put("eventType", tblEventRecordMapper.translateEventType(tblEventRecord.getEventType()));
            event.put("eventTime", tblEventRecord.getEventTime());
            event.put("locationInterval", tblEventRecord.getLocationInterval());
            event.put("deviceId", tblEventRecord.getSzSourceCode());
            event.put("img", tblEventRecord.getEventPhoto());
            event.put("video", tblEventRecord.getVideo());

            JSONObject data = new JSONObject();
            data.put("type", "eventOccur");
            data.put("data", event.toJSONString());
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
            kafkaEnum.setData(data.toJSONString());
            remoteKafkaService.send(kafkaEnum);
        } else {
            JSONObject event;
            JSONObject data;
            for (TblEventAlarm i : list) {
                if (tblEventRecord.getEventType().equals(i.getEventType().toString())) {
                    event = new JSONObject();
                    event.put("id", tblEventRecord.getId());
                    event.put("eventType", tblEventRecordMapper.translateEventType(tblEventRecord.getEventType()));
                    event.put("eventTime", tblEventRecord.getEventTime());
                    event.put("locationInterval", tblEventRecord.getLocationInterval());
                    event.put("deviceId", tblEventRecord.getSzSourceCode());
                    event.put("img", tblEventRecord.getEventPhoto());
                    event.put("video", tblEventRecord.getVideo());

                    data = new JSONObject();
                    data.put("type", "eventOccur");
                    data.put("data", event.toJSONString());
                    KafkaEnum kafkaEnum = new KafkaEnum();
                    kafkaEnum.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
                    kafkaEnum.setData(data.toJSONString());
                    remoteKafkaService.send(kafkaEnum);
                }
            }
        }
        return r;
    }

    @Override
    public int update(TblEventRecord tblEventRecord) {
        tblEventRecord.setUpdateTime(DateUtils.getNowDate());
        tblEventRecord.setUpdateUserId(SecurityUtils.getUserId());
        return tblEventRecordMapper.updateByPrimaryKey(tblEventRecord);
    }

    @Override
    public List<TblEventRecord> event() {
        List<Integer> status = Arrays.asList(0, 1);
        Example example = new Example(TblEventRecord.class);
        example.createCriteria().andIn("status", status);
        List<TblEventRecord> eventRecordList = tblEventRecordMapper.selectByExample(example);
        List<SysDictData> sysDictDataList = DictUtils.getDictCache("event_type");
        if (!sysDictDataList.isEmpty() && sysDictDataList.size() > 0) {
            for (TblEventRecord tblEventRecord : eventRecordList) {
                for (SysDictData s : sysDictDataList) {
                    if (tblEventRecord.getEventType().equals(s.getDictValue())) {
                        tblEventRecord.setEventTypeLabel(s.getDictLabel());
                    }
                }
            }
        }
        return eventRecordList;
    }

    private void eventUpdate(Long ubiLogicId, Integer uiState, String szRemark, String szUser) {
        JSONObject data = new JSONObject();
        data.put("ubiLogicId", ubiLogicId);
        data.put("uiState", uiState);
        data.put("szRemark", szRemark);
        data.put("szUser", szUser);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.MONITOR_UPDATE_EVENT_INFO);
        kafkaEnum.setData(data.toJSONString());
        remoteKafkaService.send(kafkaEnum);
    }


    @Override
    public List<Map> filterUpEvent() {
        return tblEventRecordMapper.filterUpEvent();
    }

    @Override
    public List<Map> filterDownEvent() {
        return tblEventRecordMapper.filterDownEvent();
    }
}
