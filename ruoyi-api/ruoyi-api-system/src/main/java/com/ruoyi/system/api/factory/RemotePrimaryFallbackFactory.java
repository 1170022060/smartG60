package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemotePrimaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class RemotePrimaryFallbackFactory implements FallbackFactory<RemotePrimaryService> {
    private static final Logger log = LoggerFactory.getLogger(RemotePrimaryFallbackFactory.class);

    @Override
    public RemotePrimaryService create(Throwable cause) {
        log.error("两客一危数据服务调用失败：{}",cause.getMessage());
        return new RemotePrimaryService() {
            @Override
            public R largeTransportCollect() {
                return null;
            }

            @Override
            public R olFlowAndRateCollect() {
                return null;
            }

            @Override
            public R olFlowCollect() {
                return null;
            }

            @Override
            public R olWeightAndRateCollect() {
                return null;
            }

            @Override
            public R stationLLInfoCollect() {
                return null;
            }

            @Override
            public R totalWeightOver100Collect() {
                return null;
            }

            @Override
            public R truckOWCollect() {
                return null;
            }
        };
    }
}
