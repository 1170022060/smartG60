package com.pingok.charge.service.software.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.software.vo.SoftwareHeartbeat;
import com.pingok.charge.service.software.ISoftwareInfoService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 软件信息 服务层处理
 *
 * @author qiumin
 */
@Slf4j
@Service
public class SoftwareInfoServiceImpl implements ISoftwareInfoService {
    @Value("${daas.host}")
    private String host;

    @Async
    @Override
    public void heartbeat(SoftwareHeartbeat SoftwareHeartbeat) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(host + "/data-center/softwareInfo", JSON.toJSONString(SoftwareHeartbeat));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(SoftwareHeartbeat) + "软件状态上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(SoftwareHeartbeat) + "软件状态上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(SoftwareHeartbeat) + "软件状态上传失败：" + e.getMessage());
            }
            if(time>=7){
                break;
            }
            time += 2;
        }
    }
}
