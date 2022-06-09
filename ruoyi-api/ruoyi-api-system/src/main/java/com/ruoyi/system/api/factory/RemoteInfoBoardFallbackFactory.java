package com.ruoyi.system.api.factory;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteInfoBoardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 情报板服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteInfoBoardFallbackFactory implements FallbackFactory<RemoteInfoBoardService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteInfoBoardFallbackFactory.class);

    @Override
    public RemoteInfoBoardService create(Throwable throwable) {
        log.error("情报板服务调用失败:{}", throwable.getMessage());
        return new RemoteInfoBoardService() {

            @Override
            public R publish(JSONObject vmsPublishInfo) {
                return null;
            }
        };
    }
}
