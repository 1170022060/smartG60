package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteEventService;
import com.ruoyi.system.api.domain.event.TblEventRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 事件服务降级处理
 *
 * @author qiumin
 */
@Component
public class RemoteEventFallbackFactory implements FallbackFactory<RemoteEventService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteEventFallbackFactory.class);

    @Override
    public RemoteEventService create(Throwable throwable) {
        log.error("事件服务调用失败:{}", throwable.getMessage());
        return new RemoteEventService() {
            @Override
            public R add(TblEventRecord tblEventRecord) {
                return null;
            }
        };

    }
}
