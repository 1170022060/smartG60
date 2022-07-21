package com.pingok.monitor.kafka;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.domain.infoboard.VmsInfo;
import com.pingok.monitor.service.device.IStatusService;
import com.pingok.monitor.service.infoboard.IVmsService;
import com.pingok.monitor.service.pilotLight.IPilotLightService;
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

import java.util.List;
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
    private IPilotLightService iPilotLightService;
    @Autowired
    private IVideoEventService iVideoEventService;
    @Autowired
    private IVideoService iVideoService;
    @Autowired
    private IVdtService iVdtService;


    /**
     * 向事件识别服务器更新事件信息
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.UPDATE_EVENT_INFO, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
    public void updateEventInfo(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("updateEventInfo 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject object = JSONObject.parseObject(String.valueOf(msg));
            try {
                boolean tOf = iVideoService.eventUpdate(object.getLong("ubiLogicId"), object.getInteger("uiState"), object.getString("szRemark"), object.getString("szUser"));
                if(tOf){
                    ack.acknowledge();
                }
            } catch (Exception e) {
                log.error("updateEventInfo消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    /**
     * 上传事件视频
     *
     * @param record
     * @param ack
     * @param topic
     */
    @KafkaListener(topics = KafkaTopIc.UPDATE_EVENT_VIDEO, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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
    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_SERVER_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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
    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_PING_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_SWITCH_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_FIREWALL_STATUS, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_INFOBOARD_PUBLISH, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
    public void infoBoardPublish(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            log.info("infoBoardPublish 消费了： Topic:" + topic + ",Message:" + message.get());
            try {
                iVmsService.publish(message.get().toString());
                ack.acknowledge();
            } catch (Exception e) {
                log.error("infoBoardPublish消费者，Topic" + topic + ",Message:" + message.get() + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_PILOTLIGHT, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
    public void pilotLightHandle(ConsumerRecord<?, ?> record, Acknowledgment ack, @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        Optional message = Optional.ofNullable(record.value());
        if (message.isPresent()) {
            Object msg = message.get();
            log.info("pilotLightHandle 消费了： Topic:" + topic + ",Message:" + msg);
            JSONObject jo = JSONObject.parseObject(String.valueOf(msg));
            String type = (String) jo.get("kafkaType");
            boolean ret = false;
            try {
                switch (type) {
                    case "passwordV1":
                        ret = iPilotLightService.passwordV1(jo);
                        break;
                    case "passwordV2":
                        ret = iPilotLightService.passwordV2(jo);
                        break;
                    case "user":
                        ret = iPilotLightService.getUserInfo();
                        break;
                    case "updateUserV1":
                        ret = iPilotLightService.updateUserInfoV1(jo);
                        break;
                    case "updateUserV2":
                        ret = iPilotLightService.updateUserInfoV2(jo);
                        break;
                    case "roadV1":
                        ret = iPilotLightService.getRoadInfoV1(jo);
                        break;
                    case "roadV2":
                        ret = iPilotLightService.getRoadInfoV2(jo);
                        break;
                    case "roadById":
                        ret = iPilotLightService.getRoadInfoById(jo);
                        break;
                    case "deviceListV1":
                        ret = iPilotLightService.getRoadDeviceInfoV1(jo);
                        break;
                    case "deviceListV2":
                        ret = iPilotLightService.getRoadDeviceInfoV2(jo);
                        break;
                    case "oneDeviceListV1":
                        ret = iPilotLightService.getOneRoadDeviceInfoV1(jo);
                        break;
                    case "oneDeviceListV2":
                        ret = iPilotLightService.getOneRoadDeviceInfoV2(jo);
                        break;
                    case "userDeviceList":
                        ret = iPilotLightService.getDeviceInfo(jo);
                        break;
                    case "commandSendV1":
                        ret = iPilotLightService.sendCmdToDeviceV1(jo);
                        break;
                    case "commandSendV2":
                        ret = iPilotLightService.sendCmdToDeviceV2(jo);
                        break;
                    case "runStatusV1":
                        ret = iPilotLightService.getRtStatusV1(jo);
                        break;
                    case "runStatusV2":
                        ret = iPilotLightService.getRtStatusV2(jo);
                        break;
                    case "recStatusV1":
                        ret = iPilotLightService.getRecStatusV1(jo);
                        break;
                    case "recStatusV2":
                        ret = iPilotLightService.getRecStatusV2(jo);
                        break;
                    case "visibility":
                        ret = iPilotLightService.setVisibility(jo);
                        break;
                    case "getFirmware":
                        ret = iPilotLightService.getFirmware();
                        break;
                    case "delFirmware":
                        ret = iPilotLightService.delFirmware(jo);
                        break;
                    case "getCode":
                        ret = iPilotLightService.getCode();
                        break;
                    case "updateFirmware":
                        ret = iPilotLightService.updateFirmware(jo);
                        break;
                    case "uploadFile":
                        ret = iPilotLightService.uploadFile(jo);
                        break;
                    case "firmwareDevice":
                        ret = iPilotLightService.getFirmwareDevice(jo);
                        break;
                    case "firmwareUpgrade":
                        ret = iPilotLightService.firmwareUpgrade(jo);
                        break;
                    case "firmwareArea":
                        ret = iPilotLightService.firmwareVerByArea(jo);
                        break;
                }
                if (ret) ack.acknowledge();
            } catch (Exception e) {
                log.error("pilotLightHandle消费者，Topic" + topic + ",Message:" + msg + "处理失败。错误信息：" + e.getMessage());
            }
        }
    }

    @KafkaListener(topics = KafkaTopIc.MONITOR_SIGNAL_VDT, groupId = KafkaGroup.MONITOR_SIGNAL_GROUP)
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
}
