package com.pingok.websocket.kafka;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pingok.websocket.service.MassageLinkApplicationService;
import com.ruoyi.common.core.kafka.KafkaGroup;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * kafka消费者
 *
 * @author 邱敏
 * @date 2022/3/13
 */

@Component
@Slf4j
public class Consumer {
    @Autowired
    private MassageLinkApplicationService massageLinkApplicationService;

    @KafkaListener(topics = KafkaTopIc.WEBSOCKET_SEND, groupId = KafkaGroup.WEBSOCKET_GROUP)
    public void send(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(msg));
            massageLinkApplicationService.cleanCacle();
            boolean tOf = massageLinkApplicationService.sendMassage(jsonObject.getString("model"), JSONObject.toJSONString(jsonObject.getJSONObject("data"), SerializerFeature.MapSortField));
            if (tOf) {
                log.info("websocket 消费了： Topic:" + topic + ",Message:" + msg);
                ack.acknowledge();
            } else {
                log.error("websocket消息推送失败--目标：" + jsonObject.getString("model") + "--内容：" + jsonObject.getJSONObject("data"));
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.WEBSOCKET_BROADCAST, groupId = KafkaGroup.WEBSOCKET_GROUP)
    public void broadcast(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            JSONObject jsonObject = JSONObject.parseObject(String.valueOf(msg));
            massageLinkApplicationService.cleanCacle();
            massageLinkApplicationService.sendMassage(JSONObject.toJSONString(jsonObject.getJSONObject("data"), SerializerFeature.MapSortField));
            ack.acknowledge();
            log.info("broadcast 消费了： Topic:" + topic + ",Message:" + msg);
        }
    }
}
