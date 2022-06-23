package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysPost;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.factory.RemotePostFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 岗位服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remotePostService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemotePostFallbackFactory.class)
public interface RemotePostService {
    /**
     * 新增岗位
     *
     * @return 结果
     */
    @PostMapping("/post/addInner")
    R addInner(@Validated @RequestBody SysPost post, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);


    /**
     * 根据ID获取岗位详细信息
     */
    @GetMapping("/post/getInfoInner/{postId}")
    R<SysPost> getInfoInner(@PathVariable(value = "postId") Long postId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    @GetMapping("/post/getInfoByNameInner/{postName}")
    R<SysPost> getInfoByNameInner(@PathVariable(value = "postName") String postName, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
