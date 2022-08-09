package com.pingok.charge.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.domain.sectorlog.vo.SectorLogVo;
import com.pingok.charge.service.device.IStatusService;
import com.pingok.charge.service.sectorlog.ISectorLogService;
import com.pingok.charge.service.specialRecord.ISpecialRecordService;
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
    private IStatusService iStatusService;
    @Autowired
    private ISpecialRecordService iSpecialRecordService;
    @Autowired
    private ISectorLogService iSectorLogService;

    @KafkaListener(topics = KafkaTopIc.SECTOR_LOG, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void sectorLog(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("sectorLog 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
            try {
                SectorLogVo sectorLogVo = iSectorLogService.getSectorLog( object.getString("laneHex"), object.getString("gid"));
                if (sectorLogVo != null) {
                    iSectorLogService.updateSectorLog(sectorLogVo);
                }
                ack.acknowledge();
            } catch (Exception e) {
                log.error("sectorLog消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_SERVER_STATUS, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void serverStatus(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("serverStatus 消费了： Topic:" + topic + ",Message:" + msg);
            JSONArray array = JSONArray.parseArray(String.valueOf(msg));
            try {
                iStatusService.serverStatus(array);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("serverStatus消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.SPECIAL_RECORD, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void specialRecord(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("specialRecord 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
            try {
                iSpecialRecordService.handleSpecial(object);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("specialRecord消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.CHARGE_SIGNAL_PING_STATUS, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void pingStatus(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("pingStatus 消费了： Topic:" + topic + ",Message:" + msg);
            JSONArray array = JSONArray.parseArray(String.valueOf(msg));
            try {
                iStatusService.pingStatus(array);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("pingStatus消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.CHARGE_SIGNAL_SWITCH_STATUS, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void switchStatus(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("switchStatus 消费了： Topic:" + topic + ",Message:" + msg);
            JSONArray array = JSONArray.parseArray(String.valueOf(msg));
            try {
                iStatusService.switchStatus(array);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("switchStatus消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.CHARGE_SIGNAL_FIREWALL_STATUS, groupId = KafkaGroup.CHARGE_SIGNAL_GROUP)
    public void firewallStatus(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("firewallStatus 消费了： Topic:" + topic + ",Message:" + msg);
            JSONArray array = JSONArray.parseArray(String.valueOf(msg));
            try {
                iStatusService.firewallStatus(array);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("firewallStatus消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

}
