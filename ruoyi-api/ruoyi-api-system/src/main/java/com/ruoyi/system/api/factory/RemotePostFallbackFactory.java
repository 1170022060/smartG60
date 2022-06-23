package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemotePostService;
import com.ruoyi.system.api.domain.SysPost;
import com.ruoyi.system.api.domain.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 岗位服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemotePostFallbackFactory implements FallbackFactory<RemotePostService> {
    private static final Logger log = LoggerFactory.getLogger(RemotePostFallbackFactory.class);

    @Override
    public RemotePostService create(Throwable throwable) {
        log.error("岗位服务调用失败:{}", throwable.getMessage());
        return new RemotePostService() {

            @Override
            public R addInner(SysPost post, String source) {
                return null;
            }

            @Override
            public R<SysPost> getInfoInner(Long postId, String source) {
                return null;
            }

            @Override
            public R<SysPost> getInfoByNameInner(String postName, String source) {
                return null;
            }

        };
    }
}
