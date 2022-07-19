package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteReleaseService;
import com.ruoyi.system.api.RemoteVdtMonitorService;
import com.ruoyi.system.api.domain.release.TblReleasePreset;
import com.ruoyi.system.api.domain.release.TblReleaseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author
 * @time 2022/7/6 10:17
 */
@Component
public class RemoteVdtMonitorFallbackFactory implements FallbackFactory<RemoteVdtMonitorService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteReleaseFallbackFactory.class);

    @Override
    public RemoteVdtMonitorService create(Throwable throwable)
    {
        log.error("车检器状态服务调用失败:{}", throwable.getMessage());
        return () -> null;
    }
}