package com.pingok.kafka.service.impl;

import com.pingok.kafka.domain.TblKafkaFailInfo;
import com.pingok.kafka.mapper.TblKafkaFailInfoMapper;
import com.pingok.kafka.service.KafkaService;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    private TblKafkaFailInfoMapper tblKafkaFailInfoMapper;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Override
    public void send(TblKafkaFailInfo tblKafkaFailInfo) {
        try {
            kafkaTemplate.send(tblKafkaFailInfo.getTopIc(), tblKafkaFailInfo.getData());
        } catch (Exception e) {
            log.error("生产者发送kafka消息失败,topIc="+tblKafkaFailInfo.getTopIc()+",错误信息：" + e.getMessage());
            tblKafkaFailInfo.setId(remoteIdProducerService.nextId());
            tblKafkaFailInfo.setSendTime(DateUtils.getNowDate());
            tblKafkaFailInfoMapper.insert(tblKafkaFailInfo);
        }
    }

    @Override
    public List<TblKafkaFailInfo> findAll() {
        return tblKafkaFailInfoMapper.selectAll();
    }

    @Override
    public int delete(Long id) {
        return tblKafkaFailInfoMapper.deleteByPrimaryKey(id);
    }
}
