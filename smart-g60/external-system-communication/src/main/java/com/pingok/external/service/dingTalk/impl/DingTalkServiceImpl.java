package com.pingok.external.service.dingTalk.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pingok.external.service.dingTalk.IDingTalkService;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.redis.service.RedisService;
import com.ruoyi.system.api.RemoteIdProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DingTalkServiceImpl implements IDingTalkService {

    public static final String DING_TALK_TOKEN = "dingTalkToken";

    @Value("${DingTalk.host}")
    private String host;

    @Value("${DingTalk.appKey}")
    private String appKey;

    @Value("${DingTalk.appSecret}")
    private String appSecret;

    @Autowired
    private RemoteIdProducerService remoteIdProducerService;
    @Autowired
    private RedisService redisService;

    @Override
    public void gettoken() {
        Map<String, Object> param = new HashMap<>();
        param.put("appkey", appKey);
        param.put("appsecret", appSecret);
        String r = HttpUtil.get(host + "/gettoken", param);
        if (!StringUtils.isEmpty(r)) {
            JSONObject object = JSONObject.parseObject(r);
            if (object.getInteger("errcode") == 0) {
                redisService.setCacheObject(DING_TALK_TOKEN, object.getString("access_token"), object.getLong("expires_in"), TimeUnit.SECONDS);
            } else {
                throw new SecurityException("获取DingTalkToken失败：" + object.getString("errmsg"));
            }
        }
    }

    @Override
    public void updateDepartmentList(Long deptId) {
        if (!redisService.hasKey(DING_TALK_TOKEN)) {
            gettoken();
        }
        String toke = redisService.getCacheObject(DING_TALK_TOKEN);
        Map<String, Object> param = new HashMap<>();
        param.put("access_token", toke);
        param.put("dept_id", deptId);
        param.put("language", "zh_CN");
        String r = HttpUtil.post(host + "/topapi/v2/department/listsub", param);
        if (!StringUtils.isEmpty(r)) {
            JSONObject object = JSONObject.parseObject(r);
            if (object.getInteger("errcode") == 0) {
                JSONArray array = object.getJSONArray("result");
                if (!array.isEmpty() && array.size() > 0) {
                    int size = array.size();
                    for(int i=0;i<size;i++){
                        object = array.getJSONObject(i);
                        log.info(object.toJSONString());
                    }
                }
            } else {
                throw new SecurityException("获取钉钉部门列表失败：" + object.getString("errmsg"));

            }
        }
    }
}
