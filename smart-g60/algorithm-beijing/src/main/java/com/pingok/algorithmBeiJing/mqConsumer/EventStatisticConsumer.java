package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadStatisEventInfo;
import com.pingok.algorithmBeiJing.service.IRoadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 统计类事件监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVASERVER",
        topic = "Event_Statistic",
        selectorExpression = "*")
public class EventStatisticConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;


    @Override
    public void onMessage(String message) {
        log.info("统计类事件监听：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        TblRoadStatisEventInfo tblRoadStatisEventInfo = new TblRoadStatisEventInfo();
        tblRoadStatisEventInfo.setRoadId(object.getString("rode_id"));
        tblRoadStatisEventInfo.setMessage(object.getString("message"));
        tblRoadStatisEventInfo.setCongestion(object.getInteger("congestion"));
        tblRoadStatisEventInfo.setType(object.getInteger("type"));
        tblRoadStatisEventInfo.setExceedCar(object.getString("exceed_car"));
        tblRoadStatisEventInfo.setPrediction(object.getString("prediction"));
        tblRoadStatisEventInfo.setStartTime(object.getDate("start_time"));
        tblRoadStatisEventInfo.setEndTime(object.getDate("end_time"));
        iRoadService.addRoadStatisEvent(tblRoadStatisEventInfo);
    }
}
