package com.ruoyi.monitorExternalSystem.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.monitorExternalSystem.config.HostConfig;
import com.ruoyi.monitorExternalSystem.service.IPrimaryGpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PrimaryGpsServiceImpl implements IPrimaryGpsService {
    @Override
    public void getPrimaryGps(JSONObject params) {
        try {
            if (!StringUtils.isEmpty(params)) {
                sendData(params);
            }else {
            log.error("两客一危 GPS 数据获取失败：" + params);
            }
        } catch (Exception ex) {
                log.error("两客一危 GPS 数据获取异常：", ex.getMessage());
        }
    }

    private void sendData(JSONObject result){
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/external-system/primaryGps/getPrimaryGps", JSON.toJSONString(result));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "转发两客一危 GPS 数据失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "转发两客一危 GPS 数据状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "转发两客一危 GPS 数据异常：" + e.getMessage());
            }
            time += 2;
        }
    }
}
