package com.pingok.event.service.radarCamera.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.TblEventHandle;
import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.domain.radarCamera.TblRadarCameraLog;
import com.pingok.event.mapper.TblEventHandleMapper;
import com.pingok.event.mapper.TblEventRecordMapper;
import com.pingok.event.service.radarCamera.IRadarCameraService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.TblKafkaFailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @time 2022/7/15 10:25
 */
@Slf4j
@Service
public class RadarCameraServiceImpl implements IRadarCameraService {

    private static List<String> eventList = Arrays.asList(
            "", "拥堵","停车","逆行","行人","烟雾","压线",
            "黑名单数据","超速","变道","掉头","机动车占用非机动车位","加塞"
    );
    private static List<String> dirList = Arrays.asList(
            "","上行","下行", "双向","由东向西","由南向北","由西向东","由北向南","其它"
    );

    @Autowired
    private TblEventRecordMapper tblEventRecordMapper;
    @Autowired
    private TblEventHandleMapper tblEventHandleMapper;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public void handleTraffic(JSONObject data) {
        TblRadarCameraLog info = JSON.parseObject(data.toJSONString(), TblRadarCameraLog.class);
        TblEventRecord rec = new TblEventRecord();
        rec.setId(remoteIdProducerService.nextId());
        rec.setCreateTime(DateUtils.getNowDate());
        rec.setCreateUserId(SecurityUtils.getUserId());
        rec.setStatus(0);
        rec.setLocationInterval(info.getDevId());
        rec.setEventTime(DateUtils.parseDate(info.getTime()));
        rec.setEventType(cvtEventType(info.getEventType()));
        rec.setDirection(cvtDirection(info.getDirection()));
        rec.setLane(info.getLaneNo());
        rec.setEventPhoto(info.getPicFilename());
        tblEventRecordMapper.insert(rec);

        TblEventHandle evt = new TblEventHandle();
        evt.setId(remoteIdProducerService.nextId());
        evt.setEventId(rec.getId());
        evt.setHandleTime(rec.getEventTime());
        evt.setCreateTime(DateUtils.getNowDate());
        evt.setUserId(SecurityUtils.getUserId());
        evt.setHandleContent("事件发生");
        tblEventHandleMapper.insert(evt);

        JSONObject jo = new JSONObject();
        jo.put("id", rec.getId());
        jo.put("eventType", rec.getEventType());
        jo.put("time", rec.getEventTime());
        jo.put("coordinate", rec.getLocationInterval());
        jo.put("img", rec.getEventPhoto());

        JSONObject joData = new JSONObject();
        data.put("type", "eventOccur");
        data.put("data", jo.toJSONString());
        TblKafkaFailInfo tblKafkaFailInfo = new TblKafkaFailInfo();
        tblKafkaFailInfo.setTopIc(KafkaTopIc.WEBSOCKET_BROADCAST);
        tblKafkaFailInfo.setData(data.toJSONString());
        remoteKafkaService.send(tblKafkaFailInfo);
    }

    String cvtEventType(Integer t) {
        int i = (int) (Math.log(t) / Math.log(2));
        if(i > eventList.size() - 1) return "未知事件";
        return eventList.get(i);
    }

    String cvtDirection(Integer i) {
        if(i > dirList.size() - 1) return "未知方向";
        return dirList.get(i);
    }
}
