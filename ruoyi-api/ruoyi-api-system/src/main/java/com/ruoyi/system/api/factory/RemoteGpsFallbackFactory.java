package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteGpsService;
import com.ruoyi.system.api.domain.baidu.TblBaiDuMapRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * gps服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteGpsFallbackFactory implements FallbackFactory<RemoteGpsService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteGpsFallbackFactory.class);

    @Override
    public RemoteGpsService create(Throwable throwable) {
        log.error("gps服务调用失败:{}", throwable.getMessage());
        return new RemoteGpsService() {
            @Override
            public R getCarsGps() {
                return null;
            }
        };

    }
}
