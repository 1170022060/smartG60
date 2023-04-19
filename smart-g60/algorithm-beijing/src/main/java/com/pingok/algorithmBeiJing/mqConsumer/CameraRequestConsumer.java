package com.pingok.algorithmBeiJing.mqConsumer;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteVodService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 请求视频流
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "JAVA_CAMERA",
        topic = "Camera_Request",
        selectorExpression = "*")
public class CameraRequestConsumer implements RocketMQListener<String> {
    @Autowired
    private RemoteVodService remoteVodService;
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public void onMessage(String s) {
        log.info("请求视频流路段id:{}", s);
        List<Integer> roadIds = JSONUtil.parseObj(s).getJSONArray("road_id").stream().map(item -> Integer.parseInt((String) item)).collect(Collectors.toList());
        // 获取视频流地址
        R vodStremList = remoteVodService.getVodStremList(roadIds);
        JSONObject jsonObject = JSONUtil.parseObj(vodStremList);
        log.info("视频流返回数据：{}", jsonObject);
        rocketMQTemplate.syncSend("Camera_Address",jsonObject );
    }
}
