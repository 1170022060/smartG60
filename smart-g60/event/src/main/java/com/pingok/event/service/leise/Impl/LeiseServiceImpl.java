package com.pingok.event.service.leise.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.event.domain.TblEventRecord;
import com.pingok.event.domain.leise.TblLeiseEvent;
import com.pingok.event.domain.leise.TblLeiseObject;
import com.pingok.event.mapper.leise.TblLeiseEventMapper;
import com.pingok.event.mapper.leise.TblLeiseObjectMapper;
import com.pingok.event.service.IEventService;
import com.pingok.event.service.leise.ILeiseService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author
 * @time 2022/5/24 11:00
 */
@Slf4j
@Service
public class LeiseServiceImpl implements ILeiseService {

//    private static long _id = 32;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblLeiseObjectMapper tblLeiseObjectMapper;
    @Autowired
    private TblLeiseEventMapper tblLeiseEventMapper;
    @Autowired
    private IEventService iEventService;

    private static List<String> eventList = Arrays.asList(
            "", "超速","低速/拥堵","异常变道","异常停⻋","⻋距过近","碰撞预警",
            "其他异常(包含抛撒物,⾏⼈等)","匝道并⼊碰撞预警"
    );

    @Async
    @Override
    public Boolean handleObject(JSONObject data) {
        try {
            TblLeiseObject tblLeiseObject = JSON.parseObject(data.toJSONString(), TblLeiseObject.class);
            tblLeiseObjectMapper.insert(tblLeiseObject);
        }catch (Exception e){
            log.error("处理雷摄数据异常：" + data.toJSONString());
        }
        return true;
    }

    @Async
    @Override
    public Boolean handleEvent(JSONObject data) {
        try{
            TblLeiseEvent tblLeiseEvent = JSON.parseObject(data.toJSONString(), TblLeiseEvent.class);
            tblLeiseEventMapper.insert(tblLeiseEvent);

            TblEventRecord tblEventRecord = new TblEventRecord();
            tblEventRecord.setId(remoteIdProducerService.nextId());
            tblEventRecord.setEventType(eventType2Str(tblLeiseEvent.getType()));
            tblEventRecord.setEventPhoto(tblLeiseEvent.getUrlPicture());
            tblEventRecord.setLane(tblLeiseEvent.getLane());
            tblEventRecord.setEventTime(tblLeiseEvent.getTime());
            tblEventRecord.setVehPlate(tblLeiseEvent.getLicenseNum());
            tblEventRecord.setVideo(tblLeiseEvent.getUrlVideo());
            tblEventRecord.setLocationInterval(String.format("%s,%s", tblLeiseEvent.getPositionLon(), tblLeiseEvent.getPositionLat()));
            iEventService.insert(tblEventRecord);
        }catch (Exception e){
            log.error("处理雷摄数据异常：" + data.toJSONString());
        }
        return true;
    }

    private String eventType2Str(int type) {
        if(type > eventList.size()) {
            return "未知事件";
        }
        return eventList.get(type);
    }
}
