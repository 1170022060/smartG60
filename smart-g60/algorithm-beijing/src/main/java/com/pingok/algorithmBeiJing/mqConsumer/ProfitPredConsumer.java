package com.pingok.algorithmBeiJing.mqConsumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.algorithmBeiJing.domain.TblRoadProfitPred;
import com.pingok.algorithmBeiJing.domain.TblRoadStatisEventInfo;
import com.pingok.algorithmBeiJing.service.IRoadService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 收益预测数据监听
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVASERVER",
        topic = "",
        selectorExpression = "*")
public class ProfitPredConsumer implements RocketMQListener<String> {

    @Autowired
    private IRoadService iRoadService;


    @Override
    public void onMessage(String message) {
        log.info("收益预测数据监听：message:{}", message);
        JSONObject object = JSON.parseObject(message);
        TblRoadProfitPred tblRoadProfitPred = new TblRoadProfitPred();
        tblRoadProfitPred.setPredProfit(object.getBigDecimal("pred_profit"));
        tblRoadProfitPred.setPredDate(object.getDate("pred_date"));
        tblRoadProfitPred.setPredFlow(object.getBigDecimal("pred_flow"));
        iRoadService.addRoadProfitPred(tblRoadProfitPred);
    }
}
