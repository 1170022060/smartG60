package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.service.IRocketMqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 轨迹数据请求监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVASERVER",
        topic = "Trajectory_Data",
        selectorExpression = "request")
public class TrajectoryDataConsumer implements RocketMQListener<String> {

    @Autowired
    private IRocketMqService iRocketMqService;

    @Override
    public void onMessage(String message) {
        log.info("轨迹数据请求监听到消息：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        if (object != null) {
            iRocketMqService.trajectoryData(object.getString("plate"),object.getString("instance_id"),object.getString("start_time"),object.getString("end_time"));
        }
    }
}
