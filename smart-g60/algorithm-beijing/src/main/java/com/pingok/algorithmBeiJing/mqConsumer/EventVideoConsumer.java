package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadVideoEventInfo;
import com.pingok.algorithmBeiJing.service.IRoadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 视频类事件监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVASERVER",
        topic = "Event_Video",
        selectorExpression = "*")
public class EventVideoConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;


    @Override
    public void onMessage(String message) {
        log.info("视频类事件监听：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        TblRoadVideoEventInfo tblRoadVideoEventInfo = new TblRoadVideoEventInfo();
        tblRoadVideoEventInfo.setType(object.getInteger("type"));
        tblRoadVideoEventInfo.setWarning(object.getString("warning"));
        tblRoadVideoEventInfo.setTrack(object.getString("track"));
        tblRoadVideoEventInfo.setCameraId(object.getLong("camera_id"));
        iRoadService.addRoadVideoEvent(tblRoadVideoEventInfo);
    }
}
