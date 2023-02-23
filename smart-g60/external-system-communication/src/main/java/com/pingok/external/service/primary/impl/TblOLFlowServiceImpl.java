package com.pingok.external.service.primary.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.domain.primary.TblOlFlowInfo;
import com.pingok.external.mapper.primary.TblOlFlowInfoMapper;
import com.pingok.external.service.primary.ITblOLFlowService;
import com.ruoyi.common.core.kafka.KafkaTopIc;
import com.ruoyi.system.api.RemoteIdProducerService;
import com.ruoyi.system.api.RemoteKafkaService;
import com.ruoyi.system.api.domain.kafuka.KafkaEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@Service
public class TblOLFlowServiceImpl implements ITblOLFlowService {

    @Autowired
    private RemoteKafkaService remoteKafkaService;
    @Autowired
    private RemoteIdProducerService remoteIdProducerService;

    @Autowired
    private TblOlFlowInfoMapper tblOlFlowMapper;

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
            kafkaEnum.setTopIc(KafkaTopIc.PRIMARY_VEHICLE_OLFLOW);
            kafkaEnum.setData(JSON.toJSONString(params));
            remoteKafkaService.send(kafkaEnum);
        } catch (Exception e) {
            log.error("超限流量数据获取异常：" + e.getMessage());
            ret = -1;
        }
    }

    @Override
    public void getOLFlowInfo(JSONArray result) {
//        将从其他服务拿到的数据入库
        for (int i = 0; i < result.size(); ++i) {

            JSONObject obj = result.getJSONObject(i);

            TblOlFlowInfo OLFlowInfo = new TblOlFlowInfo();
            OLFlowInfo.setId(remoteIdProducerService.nextId());
            OLFlowInfo.setStatDate(obj.getString("statDate"));
            OLFlowInfo.setNodeName(obj.getString("nodeName"));
            OLFlowInfo.setNodeId(obj.getString("nodeId"));
            OLFlowInfo.setTotalFlow(obj.getLong("totalFlow"));
            OLFlowInfo.setOverLoadFlow(obj.getLong("overLoadFlow"));
            OLFlowInfo.setRate(obj.getBigDecimal("rate"));
            OLFlowInfo.setInterceptFlow(obj.getLong("interceptFlow"));
            OLFlowInfo.setSecondReviewFlow(obj.getLong("secondReviewFlow"));
            OLFlowInfo.setSecondReviewRate(obj.getBigDecimal("secondReviewRate"));
            tblOlFlowMapper.insert(OLFlowInfo);
        }
    }
}
