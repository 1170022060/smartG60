package com.pingok.kafka.service.impl;

import com.pingok.kafka.domain.KafkaEnum;
import com.pingok.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public void send(KafkaEnum kafkaEnum) {
        kafkaTemplate.send(kafkaEnum.getTopIc(), kafkaEnum.getData());
    }

}
