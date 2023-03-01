package com.pingok.gis.kafka;

import com.alibaba.fastjson.JSONObject;
import com.pingok.gis.service.IGisService;
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
 * GIS消费者
 *
 * @author 邱敏
 * @date 2022/3/13
 */

@Component
@Slf4j
public class Consumer {

    @Autowired
    private IGisService iGisService;

    @KafkaListener(topics = KafkaTopIc.GIS_UPDATE_STATUS, groupId = KafkaGroup.GIS_GROUP)
    public void updateStatus(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("updateStatus 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
            try {
                iGisService.updateStatus(object.getString("code"), object.getInteger("status"), object.getString("type"));
                ack.acknowledge();
            } catch (Exception e) {
                log.error("updateStatus消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

}
