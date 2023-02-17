package com.ruoyi.monitorExternalSystem.kafka;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.kafka.KafkaGroup;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.monitorExternalSystem.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class Consumer {
    @Autowired
    private IOLFlowService iOLFlowService;
    @Autowired
    private ITruckOWService iTruckOWService;
    @Autowired
    private IStationLLService iStationLLService;
    @Autowired
    private IOlFlowAndRateService iOlFlowAndRateService;
    @Autowired
    private IOlWeightAndRateService iOlWeightAndRateService;
    @Autowired
    private IPrimaryGpsService iPrimaryGpsService;
    @Autowired
    private ITotalWeightOver100Service iTotalWeightOver100Service;
    @Autowired
    private ILargeTransportService iLargeTransportService;
    @Autowired IOwService iOwService;

//    货车超重分布数据获取
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_TRUCKOW, groupId = KafkaGroup.PRIMARY_VEHICLE_TRUCKOW_GROUP)
    public void truckOWCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("truckOWCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iTruckOWService.getTruckOWInfo(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("truckOWCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
//    超限流量数据获取
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_OLFLOW, groupId = KafkaGroup.PRIMARY_VEHICLE_OLFLOW_GROUP)
    public void olFlowCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("olFlowCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iOLFlowService.getOLFlowInfo(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("olFlowCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    //收费站经纬度信息
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_STATIONLL, groupId = KafkaGroup.PRIMARY_VEHICLE_STATIONLL_GROUP)
    public void stationLLCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("stationLLCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                iStationLLService.getStationLLInfo();
                ack.acknowledge();
            } catch (Exception e) {
                log.error("stationLLCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_OLFLOW_AND_RATE, groupId = KafkaGroup.PRIMARY_VEHICLE_OLFLOW_AND_RATE_GROUP)
    public void olFlowAndRateCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("olFlowAndRateCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iOlFlowAndRateService.getOlFlowAnsRateInfo(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("olFlowAndRateCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_OLWEIGHT_AND_RATE, groupId = KafkaGroup.PRIMARY_VEHICLE_OLWEIGHT_AND_RATE_GROUP)
    public void olWeightAndRateCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("olWeightAndRateCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iOlWeightAndRateService.getOlWeightAndRateInfo(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("olWeightAndRateCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_WEIGHT_OVER100, groupId = KafkaGroup.PRIMARY_VEHICLE_WEIGHT_OVER100_GROUP)
    public void totalWeightOver100Collect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("totalWeightOver100Collect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iTotalWeightOver100Service.getTotalWeightOver100(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("totalWeightOver100Collect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.PRIMARY_VEHICLE_LARGE_TRANSPORT, groupId = KafkaGroup.PRIMARY_VEHICLE_LARGE_TRANSPORT_GROUP)
    public void largeTransportCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("largeTransportCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iLargeTransportService.getLargeTransport(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("largeTransportCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.G60, groupId = KafkaGroup.G60_GROUP)
    public void primaryGpsCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("primaryGpsCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iPrimaryGpsService.getPrimaryGps(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("primaryGpsCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.G60_OVER_VEHICLE, groupId = KafkaGroup.G60_OVER_VEHICLE_GROUP)
    public void OwCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("OwCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iOwService.getOwInfo(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("OwCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
}
