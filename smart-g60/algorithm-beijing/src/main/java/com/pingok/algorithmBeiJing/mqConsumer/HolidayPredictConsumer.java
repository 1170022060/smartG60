package com.pingok.algorithmBeiJing.mqConsumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 节假日预测预测流量
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVA_HOLIDAY",
        topic = "HolidayPredict_out",
        selectorExpression = "*")
public class HolidayPredictConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("节假日预测预测流量流速监听：message:{}", message);
    }
}
