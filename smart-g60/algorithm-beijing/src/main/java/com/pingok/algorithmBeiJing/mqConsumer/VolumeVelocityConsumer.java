package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadInfo;
import com.pingok.algorithmBeiJing.domain.TblRoadVolumeVelocity;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 流量流速计算结果监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVA_VOLUMEVELOCITY",
        topic = "VolumeVelocity_out",
        selectorExpression = "*")
public class VolumeVelocityConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;

    @Autowired
    private RemoteKafkaService remoteKafkaService;

    @Override
    public void onMessage(String message) {
        log.info("流量流速计算结果监听到消息：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        JSONObject flow = object.getJSONObject("flow");
        JSONObject velocity = object.getJSONObject("velocity");
        // TODO: 2023/4/10 0010 参数有变更
        JSONObject congestion = object.getJSONObject("congestion");
        List<TblRoadInfo> list = iRoadService.selectAll();
        TblRoadVolumeVelocity tblRoadVolumeVelocity;
        JSONObject gisOb = null;
        KafkaEnum kafkaEnum;
        for (TblRoadInfo r : list) {
            if (flow.containsKey(r.getRoadId()) || velocity.containsKey(r.getRoadId()) || congestion.containsKey(r.getRoadId())) {
                tblRoadVolumeVelocity = new TblRoadVolumeVelocity();
                tblRoadVolumeVelocity.setRoadId(r.getRoadId());
                tblRoadVolumeVelocity.setFlow(flow.getBigDecimal(r.getRoadId()));
                tblRoadVolumeVelocity.setVelocity(velocity.getBigDecimal(r.getRoadId()));
                tblRoadVolumeVelocity.setCongestion(congestion.getBigDecimal(r.getRoadId()));
                tblRoadVolumeVelocity.setStartTime(DateUtils.parseDate(object.getString("start_time")));
                tblRoadVolumeVelocity.setEndTime(DateUtils.parseDate(object.getString("end_time")));
                iRoadService.addRoadVolumeVelocity(tblRoadVolumeVelocity);


                if (new BigDecimal(0).compareTo(tblRoadVolumeVelocity.getCongestion()) <= 0 && new BigDecimal(2).compareTo(tblRoadVolumeVelocity.getCongestion()) > 0) {
                    gisOb = new JSONObject();
                    gisOb.put("gisId",r.getGisRoadId());
                    gisOb.put("status",0);
                }else if (new BigDecimal(2).compareTo(tblRoadVolumeVelocity.getCongestion()) <= 0 && new BigDecimal(4).compareTo(tblRoadVolumeVelocity.getCongestion()) > 0) {
                    gisOb = new JSONObject();
                    gisOb.put("gisId",r.getGisRoadId());
                    gisOb.put("status",1);
                }else if (new BigDecimal(4).compareTo(tblRoadVolumeVelocity.getCongestion()) <= 0 && new BigDecimal(5).compareTo(tblRoadVolumeVelocity.getCongestion()) > 0) {
                    gisOb = new JSONObject();
                    gisOb.put("gisId",r.getGisRoadId());
                    gisOb.put("status",2);
                }

                if(gisOb!=null){
                    kafkaEnum = new KafkaEnum();
                    kafkaEnum.setTopIc(KafkaTopIc.GIS_ROAD_STATUS_UPDATE);
                    kafkaEnum.setData(gisOb.toJSONString());
                    remoteKafkaService.send(kafkaEnum);
                }
            }
        }
    }
}
