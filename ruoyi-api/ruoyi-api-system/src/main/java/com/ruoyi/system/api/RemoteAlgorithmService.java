package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteAlgorithmFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Algorithm服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteAlgorithmService", value = ServiceNameConstants.ALGORITHM_SERVICE, fallbackFactory = RemoteAlgorithmFallbackFactory.class)
public interface RemoteAlgorithmService {

    @PostMapping("/algVehCarbonEmission/")
    R<String> saveAlgVehCarbonEmission();
}
