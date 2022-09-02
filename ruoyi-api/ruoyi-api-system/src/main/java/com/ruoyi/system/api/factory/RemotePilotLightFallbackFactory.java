package com.ruoyi.system.api.factory;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemotePilotLightService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 超视距诱导灯服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemotePilotLightFallbackFactory implements FallbackFactory<RemotePilotLightService> {
    private static final Logger log = LoggerFactory.getLogger(RemotePilotLightFallbackFactory.class);

    @Override
    public RemotePilotLightService create(Throwable throwable) {
        log.error("超视距诱导灯调用失败:{}", throwable.getMessage());
        return new RemotePilotLightService() {

            @Override
            public R sendCmdToDeviceV2(JSONObject body) {
                return null;
            }

            @Override
            public R getRtStatus() {
                return null;
            }
        };

    }
}
