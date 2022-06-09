package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteAmapService;
import com.ruoyi.system.api.domain.amap.TblAutoNaviMapRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 高德服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteAmapFallbackFactory implements FallbackFactory<RemoteAmapService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteAmapFallbackFactory.class);

    @Override
    public RemoteAmapService create(Throwable throwable) {
        log.error("第三方服务调用失败:{}", throwable.getMessage());
        return new RemoteAmapService() {


            @Override
            public R eventPublish(TblAutoNaviMapRecord autoNaviMapRecord) {
                return null;
            }

            @Override
            public R eventRelieve(Long id) {
                return null;
            }
        };

    }
}
