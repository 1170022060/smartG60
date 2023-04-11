package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadForecast;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * 预测流量流速监听 长短时预测
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVA_LONGSHORT",
        topic = "LongShortTermPredict_out",
        selectorExpression = "*")
public class ForecastConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;


    @Override
    public void onMessage(String message) {
        log.info("预测流量流速监听：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        JSONArray flowArray = object.getJSONArray("flow");
        JSONArray velocityArray = object.getJSONArray("velocity");
        JSONArray congestionArray = object.getJSONArray("congestion");
        Integer timeSlotNum = object.getInteger("time_slot_num");
        Date startTime = object.getDate("start_time");
        JSONArray roadIds = object.getJSONArray("road_ids");
        JSONArray flow;
        JSONArray velocity;
        JSONArray congestion;
        TblRoadForecast tblRoadForecast;
        if (Objects.isNull(roadIds)) {
            return;
        }
        for (int i = 0; i < roadIds.size(); i++) {
            flow = flowArray.getJSONArray(i);
            velocity = velocityArray.getJSONArray(i);
            congestion = congestionArray.getJSONArray(i);
            for (int j = 0; j < timeSlotNum; j++) {
                tblRoadForecast = new TblRoadForecast();
                tblRoadForecast.setRoadId(roadIds.getString(i));
                tblRoadForecast.setFlow(flow.getBigDecimal(j));
                tblRoadForecast.setCongestion(congestion.getBigDecimal(j));
                tblRoadForecast.setVelocity(velocity.getBigDecimal(j));
                tblRoadForecast.setStartTime(startTime);
                startTime = DateUtils.getPreTime(startTime, 1);
                tblRoadForecast.setEndTime(startTime);
                iRoadService.addRoadForecast(tblRoadForecast);
            }
        }
    }
}
