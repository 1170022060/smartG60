package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.SysDept;
import com.ruoyi.system.api.factory.RemoteDeptFallbackFactory;
import org.bouncycastle.its.asn1.IValue;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 部门服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteDeptService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteDeptFallbackFactory.class)
public interface RemoteDeptService {
    /**
     * 新增部门
     *
     * @return 结果
     */
    @PostMapping("/dept/addInner")
    R addInner(@Validated @RequestBody() SysDept dept, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 更新部门详细信息
     */
    @PostMapping("/dept/editInner")
    R editInner(@Validated @RequestBody() SysDept dept, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 根据ID获取部门详细信息
     */
    @GetMapping("/dept/getInfoInner/{deptId}")
    R<SysDept> getInfoInner(@PathVariable(value = "deptId") Long deptId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @DeleteMapping("/dept/removeInner/{deptId}")
    R removeInner(@PathVariable(value = "deptId") Long deptId, @RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
