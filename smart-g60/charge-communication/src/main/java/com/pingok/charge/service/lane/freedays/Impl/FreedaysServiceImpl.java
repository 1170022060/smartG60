package com.pingok.charge.service.lane.freedays.Impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pingok.charge.service.lane.freedays.IFreedaysService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author
 * @time 2022/5/30 12:30
 */
@Slf4j
@Service
public class FreedaysServiceImpl implements IFreedaysService {

//    @Value("${centerHost}")
//    private String centerHost;
    private String centerHost = "10.131.33.10:28080";
//    @Value("${daas.host}")
//    private String dassHost;
    private String dassHost = "localhost:9308";

    @Async
    @Override
    public Boolean send(JSONObject data) {
        String url = centerHost + "/api/BaseData/GetFreeDaysInfo";
        String post = "";
        R ret = null;
        int[] sleep = new int[] { 1,3,6 };
        int i = 0;
        while (i++ < sleep.length) {
            try {
                post = HttpUtil.post(url, data);
                if (!StringUtils.isEmpty(post)) {
                    ret = JSON.parseObject(post, R.class);
                    if (R.FAIL == ret.getCode()) {
                        log.error(data + "节假日免费信息下发失败：" + ret.getMsg());
                    } else {
                        break;
                    }
                    Thread.sleep(sleep[i++] * 1000);
                }
            } catch (Exception e) {
                log.error(data + "节假日免费信息下发异常：" + e.getMessage());
            }
        }
        return R.SUCCESS == ret.getCode();
    }

    @Async
    @Override
    public Boolean sendToDass(JSONObject data) {
        String url = dassHost + "/freeDays";
        String post = "";
        R ret = null;
        int[] sleep = new int[] { 1,3,6 };
        int i = 0;
        while (i++ < sleep.length) {
            try {
                post = HttpUtil.post(url, data);
                if (!StringUtils.isEmpty(post)) {
                    ret = JSON.parseObject(post, R.class);
                    if (R.FAIL == ret.getCode()) {
                        log.error(data + "节假日免费信息转发失败：" + ret.getMsg());
                    } else {
                        break;
                    }
                    Thread.sleep(sleep[i++] * 1000);
                }
            } catch (Exception e) {
                log.error(data + "节假日免费信息转发异常：" + e.getMessage());
            }
        }
        return R.SUCCESS == ret.getCode();
    }
}
