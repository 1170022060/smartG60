package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblOlFlowandrateInfo;
import com.pingok.external.domain.primary.TblStationLLInfo;
import com.pingok.external.mapper.primary.TblOlFlowAndRateMapper;
import com.pingok.external.mapper.primary.TblStationLLInfoMapper;
import com.pingok.external.service.primary.ITblOlFlowAndRateService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class TblOlFlowAndRateServiceImpl implements ITblOlFlowAndRateService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblOlFlowAndRateMapper tblOlFlowAndRateMapper;

    @Autowired
    private TblStationLLInfoMapper tblStationLLInfoMapper;
    @Override
    public void collect() {
        //发起kafka
        int ret = 200;
        try {
        Date dToday = new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
        String startDate = sdf.format(dToday);
        JSONObject params = new JSONObject();
        params.put("startDate", startDate);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_OLFLOW_AND_RATE);
        kafkaEnum.setData(JSON.toJSONString(params));
        remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("超限流量、超限率时间对比数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getOlFlowAndRateInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {

            JSONObject obj = result.getJSONObject(i);

            Example example = new Example(TblOlFlowandrateInfo.class);
            example.createCriteria().andEqualTo("name", obj.getString("name"));
            TblOlFlowandrateInfo OlFlowandRateInfo = tblOlFlowAndRateMapper.selectOneByExample(example);

            Example stationList = new Example(TblStationLLInfo.class);
            stationList.createCriteria().andEqualTo("nodeName", obj.getString("name"));
            TblStationLLInfo station = tblStationLLInfoMapper.selectOneByExample(stationList);
            boolean isExsit = true;
            if (StringUtils.isNull(OlFlowandRateInfo)){
                OlFlowandRateInfo = new TblOlFlowandrateInfo();
                OlFlowandRateInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }

            OlFlowandRateInfo.setName(obj.getString("name"));
            if (StringUtils.isNotNull(station)){
                OlFlowandRateInfo.setNodeId(station.getNodeId());
            }
            OlFlowandRateInfo.setData(obj.getString("data"));
            OlFlowandRateInfo.setRate(obj.getString("rate"));
            Date dToday = new Date();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            String time = sdf.format(dToday);
            OlFlowandRateInfo.setTime(time);

            if (isExsit) tblOlFlowAndRateMapper.updateByPrimaryKey(OlFlowandRateInfo);
            else tblOlFlowAndRateMapper.insert(OlFlowandRateInfo);
        }
    }
}
