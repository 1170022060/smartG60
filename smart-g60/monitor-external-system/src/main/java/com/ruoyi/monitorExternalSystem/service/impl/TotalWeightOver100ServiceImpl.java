package com.ruoyi.monitorExternalSystem.service.impl;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.monitorExternalSystem.config.HostConfig;
import com.ruoyi.monitorExternalSystem.config.PrimaryConfig;
import com.ruoyi.monitorExternalSystem.service.ITotalWeightOver100Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TotalWeightOver100ServiceImpl implements ITotalWeightOver100Service {
    @Override
    public void getTotalWeightOver100(JSONObject params) {
        String url = PrimaryConfig.HOST+ "/share/statisticsOverWeight";
        JSONArray data=new JSONArray();
        try {
            HttpResponse response = HttpRequest.post(url)
                    .header("Authorization", PrimaryConfig.TOKEN)
                    .header("Content-Type", PrimaryConfig.FONT)
                    .body(params.toJSONString()).execute();
            String result=response.body();
            if (!StringUtils.isEmpty(result)) {
                if (result.startsWith("{")) {
                    JSONObject ret = JSONObject.parseObject(result);
                    if (ret.containsKey("status") && ret.getInteger("status") == 200) {
                        data = ret.getJSONObject("data").getJSONArray("items");
                    }else{
                        log.error(ret.getString("message"));
                    }
                }else {
                    log.error("按治超站统计车货总重大于 100 吨超限量数据获取失败：" + result);
                }
            }else {
                log.error("按治超站统计车货总重大于 100 吨超限量数据获取失败：" + result);
            }
            sendData(data);
        } catch (Exception ex) {
            log.error("按治超站统计车货总重大于 100 吨超限量数据获取异常：", ex.getMessage());
        }
    }
    private void sendData(JSONArray result){
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/external-system/totalWeightOver100/getTotalWeightOver100", JSON.toJSONString(result));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "转发按治超站统计车货总重大于 100 吨超限量数据失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "转发按治超站统计车货总重大于 100 吨超限量数据状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "转发按治超站统计车货总重大于 100 吨超限量数据异常：" + e.getMessage());
            }
            time += 2;
        }
    }
}
