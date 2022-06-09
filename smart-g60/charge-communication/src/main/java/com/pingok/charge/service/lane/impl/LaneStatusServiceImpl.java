package com.pingok.charge.service.lane.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.pingok.charge.domain.lane.vo.LaneStatus;
import com.pingok.charge.service.lane.ILaneStatusService;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 车道状态 服务层处理
 *
 * @author qiumin
 */
@Slf4j
@Service
public class LaneStatusServiceImpl implements ILaneStatusService {

    @Value("${daas.host}")
    private String host;

    @Async
    @Override
    public void update(LaneStatus laneStatus) {
        String post;
        R ret;
        int time = 1;
        while (true) {
            try {
                post = HttpUtil.post(host + "/data-center/laneStatus", JSON.toJSONString(laneStatus));
                if (!StringUtils.isEmpty(post)) {
                    if (post.startsWith("{")) {
                        ret = JSON.parseObject(post, R.class);
                        if (R.SUCCESS == ret.getCode()) {
                            break;
                        } else {
                            log.error(JSON.toJSONString(laneStatus) + "车道状态上传失败：" + ret.getMsg());
                        }
                    } else {
                        log.error(JSON.toJSONString(laneStatus) + "车道状态上传状态未知");
                    }
                    Thread.sleep(time * 1000);
                }
            } catch (InterruptedException e) {
                log.error(JSON.toJSONString(laneStatus) + "车道状态上传失败：" + e.getMessage());
            }
            if(time>=7){
                break;
            }
            time += 2;
        }
    }
}
