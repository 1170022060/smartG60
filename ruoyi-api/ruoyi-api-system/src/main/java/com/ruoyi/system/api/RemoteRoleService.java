package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.factory.RemoteRoleFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 角色服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteRoleService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteRoleFallbackFactory.class)
public interface RemoteRoleService {
    /**
     * 新增角色
     *
     * @return 结果
     */
    @PostMapping("/role/addInner")
    R addInner(@Validated @RequestBody SysRole role, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 更新角色详细信息
     */
    @PutMapping("/role/editInner")
    R editInner(@Validated @RequestBody SysRole role, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据ID获取角色详细信息
     */
    @GetMapping("/role/getInfoInner/{roleId}")
    R<SysRole> getInfoInner(@PathVariable(value = "roleId") Long roleId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 删除部门
     *
     * @param roleIds
     * @return
     */
    @DeleteMapping("/role/removeInner/{roleIds}")
    R removeInner(@PathVariable(value = "roleIds") Long[] roleIds, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
