package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblOlWeightandrateInfo;
import com.pingok.external.domain.primary.TblStationLLInfo;
import com.pingok.external.mapper.primary.TblOlWeightAndRateMapper;
import com.pingok.external.mapper.primary.TblStationLLInfoMapper;
import com.pingok.external.service.primary.ITblOlWeightAndRateService;
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
import java.util.List;

@Slf4j
@Service
public class TblOlWeightAndRateServiceImpl implements ITblOlWeightAndRateService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private TblOlWeightAndRateMapper tblOlWeightAndRateMapper;

    @Autowired
    private TblStationLLInfoMapper tblStationLLInfoMapper;
    @Override
    public void collect() {//        发起kafka
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
        List<TblStationLLInfo> stationLLInfos = tblStationLLInfoMapper.getStationInfo();
        params.put("stationInfos",stationLLInfos);
        KafkaEnum kafkaEnum = new KafkaEnum();
        kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_OLWEIGHT_AND_RATE);
        kafkaEnum.setData(JSON.toJSONString(params));
        remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("超限重量占比对比数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getOlWeightAndRateInfo(JSONArray result) {
        for (int i = 0; i < result.size(); ++i) {
            JSONObject obj = result.getJSONObject(i);

            Example example = new Example(TblOlWeightandrateInfo.class);
            example.createCriteria().andEqualTo("nodeId", obj.getString("nodeId"));
            boolean isExsit = true;
            TblOlWeightandrateInfo OLWeightInfo = tblOlWeightAndRateMapper.selectOneByExample(example);

            if (StringUtils.isNull(OLWeightInfo)){
                OLWeightInfo = new TblOlWeightandrateInfo();
                OLWeightInfo.setId(remoteIdProducerService.nextId());
                isExsit = false;
            }
            OLWeightInfo.setNodeId(obj.getString("nodeId"));
            OLWeightInfo.setOverLoadFlow(obj.getLong("overLoadFlow"));
            OLWeightInfo.setTotalFlow(obj.getLong("totalFlow"));
            OLWeightInfo.setOverLoadTonflow0to5(obj.getLong("overLoadTonflow0to5"));
            OLWeightInfo.setOverloadtonflow5to10(obj.getLong("overloadtonflow5to10"));
            OLWeightInfo.setOverLoadTonflow10to30(obj.getLong("overLoadTonflow10to30"));
            OLWeightInfo.setOverLoadTonflow30to55(obj.getLong("overLoadTonflow30to55"));
            OLWeightInfo.setOverLoadTonflow55to100(obj.getLong("overLoadTonflow55to100"));
            OLWeightInfo.setOverLoadTonflow100(obj.getLong("overLoadTonflow100"));
            OLWeightInfo.setOverLoadTonrate0to50(obj.getLong("overLoadTonrate0to50"));
            OLWeightInfo.setOverLoadTonerate50(obj.getLong("overLoadTonerate50"));

            if (isExsit) tblOlWeightAndRateMapper.updateByPrimaryKey(OLWeightInfo);
            else tblOlWeightAndRateMapper.insert(OLWeightInfo);

        }
    }
}
