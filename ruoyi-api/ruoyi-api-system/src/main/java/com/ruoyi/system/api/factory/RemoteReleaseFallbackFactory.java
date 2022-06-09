package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteReleaseService;
import com.ruoyi.system.api.domain.release.TblReleasePreset;
import com.ruoyi.system.api.domain.release.TblReleaseRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 情报板发布记录服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteReleaseFallbackFactory implements FallbackFactory<RemoteReleaseService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteReleaseFallbackFactory.class);

    @Override
    public RemoteReleaseService create(Throwable throwable)
    {
        log.error("情报板发布记录服务调用失败:{}", throwable.getMessage());
        return new RemoteReleaseService()
        {
            @Override
            public R add(TblReleaseRecord tblReleaseRecord) {
                return null;
            }

            @Override
            public R<TblReleasePreset> idInfo(Long id) {
                return null;
            }
        };
    }
}
