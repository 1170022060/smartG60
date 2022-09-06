package com.ruoyi.system.api.factory;

import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.RemoteRoleService;
import com.ruoyi.system.api.domain.SysRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 角色服务降级处理
 *
 * @author ruoyi
 */
@Component
public class RemoteRoleFallbackFactory implements FallbackFactory<RemoteRoleService> {
    private static final Logger log = LoggerFactory.getLogger(RemoteRoleFallbackFactory.class);

    @Override
    public RemoteRoleService create(Throwable throwable) {
        log.error("角色服务调用失败:{}", throwable.getMessage());
        return new RemoteRoleService() {


            @Override
            public R addInner(SysRole role, String source) {
                return null;
            }

            @Override
            public R editInner(SysRole role, String source) {
                return null;
            }

            @Override
            public R<SysRole> getInfoInner(Long roleId, String source) {
                return null;
            }

            @Override
            public R removeInner(Long[] roleIds, String source) {
                return null;
            }
        };
    }
}
