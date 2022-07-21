package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.SecurityConstants;
import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemotePostFallbackFactory;
import com.ruoyi.system.api.factory.RemoteRoadDoctorFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * 道路医生服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteRoadDoctorService", value = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteRoadDoctorFallbackFactory.class)
public interface RemoteRoadDoctorService {
    /**
     * 更新病害信息
     *
     * @return 结果
     */
    @PostMapping("/roadDoctor")
    R updateDisease(@RequestHeader(SecurityConstants.FROM_SOURCE) String source);

}
