package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadCo2PerProfit;
import com.pingok.algorithmBeiJing.service.IRoadService;
import com.ruoyi.common.core.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 碳排放数据监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVA_CARBON",
        topic = "CarbonEmission_out",
        selectorExpression = "*")
public class Co2PerProfitConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;


    @Override
    public void onMessage(String message) {
        log.info("碳排放数据监听：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        TblRoadCo2PerProfit tblRoadCo2PerProfit = new TblRoadCo2PerProfit();
        tblRoadCo2PerProfit.setCo2PerProfit(object.getBigDecimal("co2_per_profit"));
        tblRoadCo2PerProfit.setIncrementPerHour(object.getBigDecimal("increment_per_hour"));
        tblRoadCo2PerProfit.setTime(DateUtils.getNowDate());
        iRoadService.addRoadCo2PerProfit(tblRoadCo2PerProfit);
    }
}
