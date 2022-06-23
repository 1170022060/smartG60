package com.ruoyi.system.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysUser;
import com.ruoyi.system.api.factory.RemoteUserFallbackFactory;
import com.ruoyi.system.api.model.LoginUser;

/**
 * 用户服务
 * 
 * @author ruoyi
 */
@FeignClient(contextId = "remoteUserService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    /**
     * 通过钉钉用户id查询用户信息
     *
     * @param dingTalkId 钉钉用户id
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/getUserInfoByDingTalkId/{dingTalkId}")
    public R<LoginUser> getUserInfoByDingTalkId(@PathVariable("dingTalkId") String dingTalkId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
    /**
     * 通过用户名查询用户信息
     *
     * @param username 用户名
     * @param source 请求来源
     * @return 结果
     */
    @GetMapping("/user/info/{username}")
    public R<LoginUser> getUserInfo(@PathVariable("username") String username, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 注册用户信息
     *
     * @param sysUser 用户信息
     * @param source 请求来源
     * @return 结果
     */
    @PostMapping("/user/register")
    public R<Boolean> registerUserInfo(@RequestBody SysUser sysUser, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 用户授权角色
     */
    @PutMapping("/user/authRoleInner")
    R authRoleInner(@RequestParam(value = "userId") Long userId, @RequestParam(value = "roleIds") Long[] roleIds, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 新增用户
     */
    @PostMapping("/user/addInner")
    R addInner(@Validated @RequestBody SysUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 修改用户
     */
    @PutMapping("/user/editInner")
    R editInner(@Validated @RequestBody SysUser user, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @DeleteMapping("/user/removeInner/{userIds}")
    R removeInner(@PathVariable(value = "userIds") Long[] userIds, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);
}
