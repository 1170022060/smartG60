package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblStationLLInfo;
import com.pingok.external.mapper.primary.TblStationLLInfoMapper;
import com.pingok.external.service.primary.ITblStationLLService;
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
public class TblStationLLServiceImpl implements ITblStationLLService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblStationLLInfoMapper tblStationLLInfoMapper;
    @Override
    public void collect() {
        //        发起kafka
        int ret = 200;
        try {
            Date dBefore = new Date();
            Date dToday = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dToday);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            dBefore = calendar.getTime();   //得到前一天的时间

            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); //设置时间格式
            String startDate = sdf.format(dBefore);    //格式化前一天
            String endDate = sdf.format(dToday);    //格式化当天
            JSONObject params = new JSONObject();
            params.put("startDate", startDate);
            params.put("endDate", endDate);
            KafkaEnum kafkaEnum = new KafkaEnum();
            kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_STATIONLL);
            kafkaEnum.setData(JSON.toJSONString(params));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("收费站经纬度信息获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getStationLLInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            Example example = new Example(TblStationLLInfo.class);
            example.createCriteria().andEqualTo("nodeId", obj.getString("nodeId"));
            TblStationLLInfo stationInfo = tblStationLLInfoMapper.selectOneByExample(example);
            boolean isExsit = true;
            if (StringUtils.isNull(stationInfo)){
                stationInfo = new TblStationLLInfo();
                stationInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }

            stationInfo.setNodeName(obj.getString("nodeName"));
            stationInfo.setNodeId(obj.getString("nodeId"));
            stationInfo.setLatitude(obj.getLong("latitude"));
            stationInfo.setLongitude(obj.getLong("longitude"));
            stationInfo.setNodeType(obj.getInteger("nodeType"));

            if (isExsit) tblStationLLInfoMapper.updateByPrimaryKey(stationInfo);
            else tblStationLLInfoMapper.insert(stationInfo);
        }
    }
}
