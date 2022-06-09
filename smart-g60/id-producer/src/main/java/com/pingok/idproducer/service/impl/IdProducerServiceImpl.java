package com.pingok.idproducer.service.impl;

import com.pingok.idproducer.mapper.IdProducerMapper;
import com.pingok.idproducer.service.IdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IdProducerServiceImpl implements IdProducerService {
    @Autowired
    private IdProducerMapper idProducerMapper;

    @Override
    public Long nextId() {
        return Long.valueOf(String.valueOf(idProducerMapper.nextId()));
    }
}
