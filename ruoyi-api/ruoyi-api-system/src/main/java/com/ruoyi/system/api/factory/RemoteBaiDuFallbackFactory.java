package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteBaiDuService;
import com.ruoyi.system.api.domain.amap.TblAutoNaviMapRecord;
import com.ruoyi.system.api.domain.baidu.TblBaiDuMapRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 百度服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteBaiDuFallbackFactory implements FallbackFactory<RemoteBaiDuService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteBaiDuFallbackFactory.class);

    @Override
    public RemoteBaiDuService create(Throwable throwable) {
        log.error("百度服务调用失败:{}", throwable.getMessage());
        return new RemoteBaiDuService() {

            @Override
            public R eventPublish(@RequestBody TblBaiDuMapRecord tblBaiDuMapRecord) {
                return null;
            }

            @Override
            public R eventRelieve(Long id) {
                return null;
            }
        };

    }
}
