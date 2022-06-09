package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteBridgeFallbackFactory;
import com.ruoyi.system.api.factory.RemoteGpsFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 桥梁服务
 *
 * @author qiumin
 */
@FeignClient(contextId = "remoteBridgeService", value = ServiceNameConstants.EXTERNAL_SERVICE, fallbackFactory = RemoteBridgeFallbackFactory.class)
public interface RemoteBridgeService {


    @GetMapping("/bridge/getWarning")
    R getWarning();

    @GetMapping("/bridge/updateAcquisition")
    R updateAcquisition();

    @GetMapping("/bridge/updateBridgeInfo")
    R updateBridgeInfo();

    @GetMapping("/bridge/updateCollection")
    R updateCollection();

    @GetMapping("/bridge/updateProjectInfo")
    R updateProjectInfo();

    @GetMapping("/bridge/updateWarning")
    R updateWarning();

    @GetMapping("/bridge/getTemperature")
    R getTemperature();
}
