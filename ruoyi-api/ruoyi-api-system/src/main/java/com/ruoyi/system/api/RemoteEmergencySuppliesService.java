package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.domain.emergency.TblEmergencySupplies;
import com.ruoyi.system.api.factory.RemoteEmergencySuppliesFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteEmergencySuppliesService", value = ServiceNameConstants.VOCATIONAL, fallbackFactory = RemoteEmergencySuppliesFallbackFactory.class)
public interface RemoteEmergencySuppliesService {

    @PostMapping("/emergencySupplies/idInfo")
    R<TblEmergencySupplies> idInfo(@RequestParam(name = "id") Long id);

}
