package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblStationLLInfo;
import com.pingok.external.domain.primary.TblTotalWeightOver100Info;
import com.pingok.external.mapper.primary.TblStationLLInfoMapper;
import com.pingok.external.mapper.primary.TblTotalWeightOver100Mapper;
import com.pingok.external.service.primary.ITblTotalWeightOver100Service;
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
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class TblTotalWeightOver100ServiceImpl implements ITblTotalWeightOver100Service {
    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblTotalWeightOver100Mapper tblTotalWeightOver100Mapper;
    @Autowired
    private TblStationLLInfoMapper tblStationLLInfoMapper;

    @Override
    public void collect() {
//        发起kafka
        int ret = 200;
        try {
            Date nowMonth = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowMonth);//把当前时间赋给日历

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
            String startDate = sdf.format(nowMonth);    //格式化当月
            JSONObject params = new JSONObject();
            params.put("checkMonth", startDate);
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_WEIGHT_OVER100);
            kafkaEnum.setData(JSON.toJSONString(params));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("按治超站统计车货总重大于 100 吨超限量数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getTotalWeightOver100(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);
            Date nowMonth = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowMonth);//把当前时间赋给日历

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM"); //设置时间格式
            String time = sdf.format(nowMonth);    //格式化当月

            Example stationList = new Example(TblStationLLInfo.class);
            stationList.createCriteria().andEqualTo("nodeName", obj.getString("stationName"));
            TblStationLLInfo station = tblStationLLInfoMapper.selectOneByExample(stationList);

            TblTotalWeightOver100Info over100Info = new TblTotalWeightOver100Info();
            over100Info.setId(remoteIdProducerService.nextId());

            if(StringUtils.isNotNull(station)){
                over100Info.setNodeId(station.getNodeId());
            }

            over100Info.setStationName(obj.getString("stationName"));
            over100Info.setOverLoadList(obj.getString("overLoadList"));
            over100Info.setReviewFlowList(obj.getString("reviewFlowList"));
            over100Info.setTime(time);

            tblTotalWeightOver100Mapper.insert(over100Info);
        }
    }
}
