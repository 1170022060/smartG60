package com.pingok.monitor.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoboard.SanSiInfo;
import com.pingok.monitor.service.device.IStatusService;
import com.pingok.monitor.service.infoboard.IFcmsService;
import com.pingok.monitor.service.infoboard.IVmsService;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import com.pingok.monitor.service.vdt.IVdtService;
import com.pingok.monitor.service.videoEvent.IVideoEventService;
import com.pingok.monitor.service.videoEvent.IVideoService;
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
    private IVmsService iVmsService;
    @Autowired
    private IFcmsService iFcmsService;
    @Autowired
    private IPilotLightService iPilotLightService;
    @Autowired
    private IVideoEventService iVideoEventService;
    @Autowired
    private IVideoService iVideoService;
    @Autowired
    private IVdtService iVdtService;

    @Autowired
    private ISmartToiletService iSmartToiletService;


    /**
     * 向事件识别服务器更新事件信息
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.MONITOR_UPDATE_EVENT_INFO, groupId = KafkaGroup.MONITOR_UPDATE_EVENT_GROUP)
    public void updateEventInfo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            ack.acknowledge();
//            Object msg = message.get();
//            log.info("updateEventInfo 消费了： Topic:" + topic + ",Message:" + msg);
//            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
//            try {
//                boolean tOf = iVideoService.eventUpdate(object.getLong("ubiLogicId"), object.getInteger("uiState"), object.getString("szRemark"), object.getString("szUser"));
//                if (tOf) {
//                    ack.acknowledge();
//                }
//            } catch (Exception e) {
//                log.error("updateEventInfo消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
//            }
        }
    }

    /**
     * 上传事件视频
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.MONITOR_UPDATE_EVENT_VIDEO, groupId = KafkaGroup.MONITOR_UPDATE_EVENT_VIDEO_GROUP)
    public void updateEventVideo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("updateEventVideo 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
            try {
                iVideoEventService.updateEventVideo(object.getLong("ubiLogicId"));
                ack.acknowledge();
            } catch (Exception e) {
                log.error("updateEventVideo消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    /**
     * 获取服务器设备状态
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_SERVER_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_SERVER_STATUS_GROUP)
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

    /**
     * 获取设备网络状态
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_PING_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_PING_STATUS_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_SWITCH_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_SWITCH_STATUS_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_FIREWALL_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_FIREWALL_STATUS_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH, groupId = KafkaGroup.MONITOR_SIGNAL_INFOBOARD_PUBLISH_GROUP)
    public void infoBoardPublish(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("infoBoardPublish 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                log.info("------------------开始发布情报板信息------------------");
                JSONObject result = iVmsService.publish(message.get().toString());
                //通知
                iVmsService.notifyResult(result);
                log.info("------------------情报板信息发布成功------------------");
                ack.acknowledge();
            } catch (Exception e) {
                log.error("infoBoardPublish消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PARKING, groupId = KafkaGroup.MONITOR_SIGNAL_INFOBOARD_PARKING_GROUP)
    public void infoBoardPublishParking(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            SanSiInfo sanSi = JSON.parseObject(String.valueOf(msg),SanSiInfo.class);
            try {
                iFcmsService.sendData(sanSi);
                log.info("infoBoardPublishParking 消费了： Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                log.error("infoBoardPublishParking，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PING, groupId = KafkaGroup.MONITOR_SIGNAL_INFOBOARD_PING_GROUP)
    public void infoBoardPing(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("infoBoardPing 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                iVmsService.collect(message.get().toString());
            } catch (Exception e) {
                log.error("infoBoardPing 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
            ack.acknowledge();
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT, groupId = KafkaGroup.MONITOR_SIGNAL_PILOTLIGHT_GROUP)
    public void pilotLightHandle(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
            String type = (String) jo.get("kafkaType");
            try {
                switch (type) {
                    case "commandSend":
                        iPilotLightService.commandSend(jo);
                        break;
                    case "updateStatus":
                        iPilotLightService.updateStatus();
                        break;
                }
                ack.acknowledge();
                log.info("pilotLightHandle 消费了： Topic:" + topic + ",Message:" + msg);
            } catch (Exception e) {
                log.error("pilotLightHandle消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_VDT, groupId = KafkaGroup.MONITOR_SIGNAL_VDT_GROUP)
    public void vdtCollect(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("vdtCollect 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                iVdtService.collect();
                ack.acknowledge();
            } catch (Exception e) {
                log.error("vdtCollect 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SMART_TOILET, groupId = KafkaGroup.MONITOR_SMART_TOILET_GROUP)
    public void smartToilet(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("smartToilet 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONArray jo = JSONArray.parseArray(String.valueOf(msg));
                iSmartToiletService.send(jo);
                ack.acknowledge();
            } catch (Exception e) {
                log.error("smartToilet 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_LINKAGE, groupId = KafkaGroup.MONITOR_LINKAGE_GROUP)
    public void linkage(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("linkage 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                Object msg = message.get();
                JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
                iVideoService.linkage(jo.getLong("ubiLogicId"));
                ack.acknowledge();
            } catch (Exception e) {
                log.error("linkage 消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }
}
