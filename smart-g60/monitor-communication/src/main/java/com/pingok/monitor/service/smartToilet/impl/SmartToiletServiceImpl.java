package com.pingok.monitor.service.smartToilet.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.monitor.config.HostConfig;
import com.pingok.monitor.service.smartToilet.ISmartToiletService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author
 * @time 2022/4/13 16:32
 */
@Slf4j
@Service
public class SmartToiletServiceImpl implements ISmartToiletService {


    @Async
    @Override
    public void sensorData(JSONObject sensorData) {
        String post;
        R ret = null;
        post = HttpUtil.post(HostConfig.DASSHOST + "/device-monitor/smartToilet", JSON.toJSONString(sensorData));
        if (!StringUtils.isEmpty(post)) {
            if (post.startsWith("{")) {
                ret = JSON.parseObject(post, R.class);
                if (R.FAIL == ret.getCode()) {
                    log.error(JSON.toJSONString(sensorData) + "智慧厕所状态上报失败：" + ret.getMsg());
                }
            }
        }
    }
}
