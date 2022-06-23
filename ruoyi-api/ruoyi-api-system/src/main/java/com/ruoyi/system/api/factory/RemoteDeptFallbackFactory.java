package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteDeptService;
import com.ruoyi.system.api.domain.SysDept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 部门服务降级处理
 * 
 * @author ruoyi
 */
@Component
public class RemoteDeptFallbackFactory implements FallbackFactory<RemoteDeptService>
{
    private static final Logger log = LoggerFactory.getLogger(RemoteDeptFallbackFactory.class);

    @Override
    public RemoteDeptService create(Throwable throwable)
    {
        log.error("部门服务调用失败:{}", throwable.getMessage());
        return new RemoteDeptService()
        {

            @Override
            public R addInner(SysDept dept, String source) {
                return null;
            }

            @Override
            public R editInner(SysDept dept, String source) {
                return null;
            }

            @Override
            public R<SysDept> getInfoInner(Long deptId, String source) {
                return null;
            }

            @Override
            public R removeInner(Long deptId, String source) {
                return null;
            }

            @Override
            public R<List<SysDept>> listInner(SysDept dept, String source) {
                return null;
            }


        };
    }
}
