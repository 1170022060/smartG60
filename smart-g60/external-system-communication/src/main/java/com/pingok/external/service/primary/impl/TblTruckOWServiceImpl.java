package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblTruckOwInfo;
import com.pingok.external.mapper.primary.TblTruckOWInfoMapper;
import com.pingok.external.service.primary.ITblTruckOWService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TblTruckOWServiceImpl implements ITblTruckOWService {
    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblTruckOWInfoMapper tblTruckOWInfoMapper;

    @Override
    public void collect() {
        // 发起kafka
        int ret = 200;
        try {
            JSONObject params = new JSONObject();
            String startDate = DateUtils.dateTime(DateUtils.getPreTime(DateUtils.getNowDate(), -60), DateUtils.YYYY_MM_DD_HH);
            String endDate = DateUtils.dateTime(DateUtils.getNowDate(), DateUtils.YYYY_MM_DD_HH);
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_TRUCKOW);
            kafkaEnum.setData(JSON.toJSONString(params));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("货车超重分布数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getTruckOWInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            TblTruckOwInfo truckOwInfo = new TblTruckOwInfo();

            truckOwInfo.setId(remoteIdProducerService.nextId());
            truckOwInfo.setStatDate(obj.getString("statDate"));
            truckOwInfo.setStatDateHour(obj.getString("statDateHour"));
            truckOwInfo.setNodeName(obj.getString("nodeName"));
            truckOwInfo.setNodeId(obj.getString("nodeId"));
            truckOwInfo.setSumTotalWeight(obj.getBigDecimal("sumTotalWeight"));

            tblTruckOWInfoMapper.insert(truckOwInfo);

        }
    }
}
