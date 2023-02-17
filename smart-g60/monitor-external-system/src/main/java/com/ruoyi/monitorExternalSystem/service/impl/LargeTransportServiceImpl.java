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
import com.ruoyi.monitorExternalSystem.service.ILargeTransportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LargeTransportServiceImpl implements ILargeTransportService {
    @Override
    public void getLargeTransport(JSONObject params) {
        String url = PrimaryConfig.HOST+ "/share/applyLargeTransportVehicle";
        JSONArray data=new JSONArray();
        int total = 0;
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
                        total = ret.getJSONObject("data").getInteger("total");
                        if (total<1000){
                            data = ret.getJSONObject("data").getJSONArray("items");
                        }else{
                            JSONObject newParams = new JSONObject();
                            newParams.put("pageNum",1);
                            newParams.put("pageSize",total-1000);
                            HttpResponse res = HttpRequest.post(url)
                                    .header("Authorization", PrimaryConfig.TOKEN)
                                    .header("Content-Type", PrimaryConfig.FONT)
                                    .body(params.toJSONString()).execute();
                            String body=res.body();
                            if (!StringUtils.isEmpty(body)) {
                                if (result.startsWith("{")) {
                                    JSONObject newResult = JSONObject.parseObject(body);
                                    if (newResult.containsKey("status") && newResult.getInteger("status") == 200) {
                                        for (int i =0;i<newResult.getJSONObject("data").getJSONArray("items").size();i++){
                                            data.add(ret.getJSONObject("data").getJSONArray("items").get(i));
                                        }
                                    }
                                }
                            }

                        }
                    }else{
                        log.error(ret.getString("message"));
                    }
                }else {
                    log.error("申请大件运输车辆信息数据获取失败：" + result);
                }
            }else {
                log.error("申请大件运输车辆信息数据获取失败：" + result);
            }
            sendData(data);
        } catch (Exception ex) {
            log.error("申请大件运输车辆信息数据获取异常：", ex.getMessage());
        }
    }
    private void sendData(JSONArray result){
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(HostConfig.DASSHOST + "/external-system/largeTransport/getLargeTransport", JSON.toJSONString(result));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(result) + "转发申请大件运输车辆信息数据失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(result) + "转发申请大件运输车辆信息数据状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(result) + "转发申请大件运输车辆信息数据异常：" + e.getMessage());
            }
            time += 2;
        }
    }
}
