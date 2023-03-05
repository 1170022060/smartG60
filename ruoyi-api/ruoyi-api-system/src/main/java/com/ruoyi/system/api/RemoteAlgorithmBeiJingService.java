package com.ruoyi.system.api;

import com.ruoyi.common.core.constant.ServiceNameConstants;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.system.api.factory.RemoteAlgorithmBeiJingFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Algorithm服务
 *
 * @author ruoyi
 */
@FeignClient(contextId = "remoteAlgorithmBeiJingService", value = ServiceNameConstants.ALGORITHM_BEIJING_SERVICE, fallbackFactory = RemoteAlgorithmBeiJingFallbackFactory.class)
public interface RemoteAlgorithmBeiJingService {

    /**
     * 保存碳排放记录
     * @return
     */
    @PostMapping("/algorithm/gantryTransactionLog")
    R gantryTransactionLog(@RequestParam(value = "startTime") String startTime, @RequestParam(value = "endTime") String endTime);

}
