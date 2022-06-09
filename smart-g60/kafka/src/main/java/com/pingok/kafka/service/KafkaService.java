package com.pingok.kafka.service;

import com.pingok.kafka.domain.TblKafkaFailInfo;

import java.util.List;

public interface KafkaService {

    void send(TblKafkaFailInfo tblKafkaFailInfo);

    List<TblKafkaFailInfo> findAll();

    int delete(Long id);

}
