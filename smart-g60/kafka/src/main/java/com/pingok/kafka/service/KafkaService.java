package com.pingok.kafka.service;

import com.pingok.kafka.domain.KafkaEnum;

public interface KafkaService {

    void send(KafkaEnum kafkaEnum);


}
